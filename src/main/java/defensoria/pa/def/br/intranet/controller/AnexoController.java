package defensoria.pa.def.br.intranet.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
import defensoria.pa.def.br.intranet.services.AnexoService;





@RestController
@RequestMapping("/anexo")
public class AnexoController {
    
    @Autowired
    AnexoService anexoService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value="/buscarAnexos", params = {"pastaAnexo"})
    public ResponseEntity<List<Anexo>> getAnexos(@RequestParam String pastaAnexo) {
        List<Anexo> anexosList = anexoService.findByPastaAnexo(pastaAnexo);
        System.out.println("AAAAAAAAA");
        if (anexosList.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(anexosList);
        }
    }
    

    @GetMapping("/buscarAnexo/{anexoNome}")
    public ResponseEntity<Resource> getAnexo(@PathVariable("anexoNome") String anexoNome) throws MalformedURLException, FileNotFoundException {        
        Resource resource = null;
        resource = anexoService.getFileAsResource(anexoNome);
        String contentType = "application/octet-stream";
        String nomeOri[] = resource.getFilename().split("\\.");
        String extensao = nomeOri[nomeOri.length - 1];
        nomeOri = null;
        String headerValue = String.format("attachment; filename=\"%s.%s\"", anexoService.selectTituloAnexoByNomeAnexo(anexoNome), extensao);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);       
    }
    
    @PostMapping(value="/salvar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnexoDTO> saveAnexo(@ModelAttribute AnexoDTO anexoDTO) throws IOException {
        Anexo anexo = convertToEntity(anexoDTO);
        anexo = anexoService.saveAnexo(anexo, anexoDTO.getArquivo());
        return ResponseEntity.ok(convertToDTO(anexo));
    }

    @PutMapping(value="/atualizar/{anexoId}")
    public ResponseEntity<Anexo> atualizarAnexo(@ModelAttribute AnexoDTO anexoDTO, @PathVariable int anexoId) throws IOException {
        Anexo anexo = convertToEntity(anexoDTO);
        if (anexoService.existsById(anexoId)) {
            anexo = anexoService.updateAnexo(anexo, anexoDTO.getArquivo());
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

    private List<AnexoDTO> convertToDTO(List<Anexo> anexoList){
        List<AnexoDTO> anexoDTO = new ArrayList<>();
        anexoList.forEach(p -> {
            anexoDTO.add(modelMapper.map(p, AnexoDTO.class));
        });
        return anexoDTO;
    }

    private Anexo convertToEntity(AnexoDTO anexoDTO){
        Anexo anexo = modelMapper.map(anexoDTO, Anexo.class);
        return anexo;
    }
    
}
