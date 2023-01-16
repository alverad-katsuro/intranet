package defensoria.pa.def.br.intranet.controller.anexo;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import defensoria.pa.def.br.intranet.dto.anexo.request.AnexoCategoriaRequestDTO;
import defensoria.pa.def.br.intranet.dto.anexo.response.AnexoCategoriaDTO;
import defensoria.pa.def.br.intranet.model.anexo.AnexoCategoria;
import defensoria.pa.def.br.intranet.services.anexo.AnexoCategoriaService;
import defensoria.pa.def.br.intranet.services.anexo.AnexoDominioService;

@RestController
@RequestMapping("/anexoCategoria")
public class AnexoCategoriaController {

    @Autowired
    AnexoCategoriaService anexoCategoriaService;

    @Autowired
    AnexoDominioService anexoDominioService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/buscarAnexoCategoria", params = "nomeAnexoCategoria")
    public ResponseEntity<AnexoCategoriaDTO> getAnexoCategoria(@RequestParam String nomeAnexoCategoria) {
        AnexoCategoria anexoCategoria = anexoCategoriaService.getAnexoCategoria(nomeAnexoCategoria);
        return ResponseEntity.ok(convertToDTO(anexoCategoria));
    }

    @GetMapping(value = "/buscarAnexoCategorias")
    public ResponseEntity<List<AnexoCategoriaDTO>> getAnexoCategorias() throws MalformedURLException, FileNotFoundException{
        List<AnexoCategoria> anexoCategoriaList = anexoCategoriaService.getAllAnexoCategoria();
        return ResponseEntity.ok(convertToDTO(anexoCategoriaList));
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<AnexoCategoriaDTO> saveAnexoCategoria(
            @RequestBody AnexoCategoriaRequestDTO anexoCategoriaRequestDTO) {
        AnexoCategoria anexoCategoria = convertToEntity(anexoCategoriaRequestDTO);
        System.out.println(anexoCategoria);
        anexoCategoria = anexoCategoriaService.saveAnexoCategoria(anexoCategoria);
        return ResponseEntity.ok(convertToDTO(anexoCategoria));
    }

    @PutMapping(value = "/atualizar/")
    public ResponseEntity<AnexoCategoriaDTO> atualizarAnexoCategoria(@RequestBody AnexoCategoriaRequestDTO anexoCategoriaRequestDTO) {
        AnexoCategoria anexoCategoria = convertToEntity(anexoCategoriaRequestDTO);
        if (anexoCategoriaService.existsById(anexoCategoria.getIdAnexoCategoria())) {
            anexoCategoria.setAnexoDominio(anexoDominioService.getAnexoDominio(anexoCategoriaRequestDTO.getIdAnexoDominio()));
            anexoCategoria = updateEntityNullValues(anexoCategoria);
            anexoCategoria = anexoCategoriaService.saveAnexoCategoria(anexoCategoria);
            return ResponseEntity.ok(convertToDTO(anexoCategoria));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { FileNotFoundException.class, MalformedURLException.class })
    public String handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return ex.getMessage();
    }

    private AnexoCategoriaDTO convertToDTO(AnexoCategoria anexoCategoria) {
        AnexoCategoriaDTO anexoCategoriaDTO = modelMapper.map(anexoCategoria, AnexoCategoriaDTO.class);
        return anexoCategoriaDTO;
    }

    private List<AnexoCategoriaDTO> convertToDTO(List<AnexoCategoria> anexoCategoriaList) throws MalformedURLException, FileNotFoundException{
        List<AnexoCategoriaDTO> anexoCategoriaDTO = new ArrayList<>();
        anexoCategoriaList.forEach(p -> {
            anexoCategoriaDTO.add(modelMapper.map(p, AnexoCategoriaDTO.class));
        });
        return anexoCategoriaDTO;
    }

    private AnexoCategoria convertToEntity(AnexoCategoriaRequestDTO anexoCategoriaRequestDTO) {
        AnexoCategoria anexoCategoria = modelMapper.map(anexoCategoriaRequestDTO, AnexoCategoria.class);
        return anexoCategoria;
    }

    private AnexoCategoria updateEntityNullValues(AnexoCategoria anexoCategoria) {
        TypeMap<AnexoCategoria, AnexoCategoria> typeMap = modelMapper.typeMap(AnexoCategoria.class, AnexoCategoria.class);
        if (anexoCategoria.getIdAnexoCategoria() != null) {
            Provider<AnexoCategoria> provider = p -> this.anexoCategoriaService.getAnexoCategoria(anexoCategoria.getIdAnexoCategoria());
            typeMap.setProvider(provider);
        }
        AnexoCategoria anexoSubCategoria = typeMap.map(anexoCategoria);
        return anexoSubCategoria;
    }
}
