package defensoria.pa.def.br.intranet.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import defensoria.pa.def.br.intranet.dto.AnexoDTO;
import defensoria.pa.def.br.intranet.model.Anexo;
import defensoria.pa.def.br.intranet.model.AnexoDominio;
import defensoria.pa.def.br.intranet.services.AnexoDominioService;
import defensoria.pa.def.br.intranet.services.AnexoService;


@RestController
@RequestMapping("/anexo")
public class AnexoController {
    
    @Autowired
    AnexoService anexoService;

    @Autowired
    AnexoDominioService anexoDominioService;

    @Autowired
    ModelMapper modelMapper;  

    @GetMapping(value="/buscarAnexos", params = {"anexoDominio"})
    public ResponseEntity<List<Anexo>> getAnexos(@RequestParam String anexoDominio) {
        List<Anexo> anexosList = anexoService.findByAnexoDominio(anexoDominio);
        if (anexosList.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(anexosList);
        }
    }
    

    @GetMapping("/buscarAnexo/{anexoNome}")
    public ResponseEntity<Resource> getAnexo(@PathVariable("anexoNome") String anexoNome) throws MalformedURLException, FileNotFoundException {        
        Resource resource = anexoService.getFileAsResource(anexoNome);
        String contentType = "application/octet-stream";
        String headerValue = String.format("attachment; filename=\"%s.%s\"", anexoService.selectTituloAnexoByNomeAnexo(anexoNome), "pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);       
    }
    
    @PostMapping(value="/salvar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnexoDTO> saveAnexo(@ModelAttribute AnexoDTO anexoDTO, @ModelAttribute Integer anexoDominioId) throws Exception {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(anexoDominioId);
        Anexo anexo = convertToEntity(anexoDTO);
        AnexoDominio anexoDominio = anexoDominioService.getAnexoDominio(anexoDominioId);
        if (anexoDominio != null) {
            anexo.setAnexoDominio(anexoDominio);
        } else {
            throw new Exception("Anexo Dominio invalido");
        }
        anexo = anexoService.saveAnexo(anexo, anexoDTO.getArquivo());
        return ResponseEntity.ok(convertToDTO(anexo));
    }

    @PutMapping(value="/atualizar/{anexoId}")
    public ResponseEntity<Anexo> atualizarAnexo(@ModelAttribute AnexoDTO anexoDTO, @PathVariable int anexoId) throws IOException {
        Anexo anexo = convertToEntity(anexoDTO);
        if (anexoService.existsById(anexoId)) {
            if (anexoDTO.getArquivo().isEmpty()) {
                anexo = anexoService.saveAnexo(anexo);
            } else {
                anexo = anexoService.updateAnexo(anexo, anexoDTO.getArquivo());
            }
            return ResponseEntity.ok(anexo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value =  {FileNotFoundException.class, MalformedURLException.class})
    public String handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return ex.getMessage();
    }
    

    private AnexoDTO convertToDTO(Anexo anexo){
        AnexoDTO anexoDTO = modelMapper.map(anexo, AnexoDTO.class);
        return anexoDTO;
    }

    private Anexo convertToEntity(AnexoDTO anexoDTO){
        Anexo anexo = modelMapper.map(anexoDTO, Anexo.class);
        return anexo;
    }
}
