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

import defensoria.pa.def.br.intranet.dto.anexo.response.AnexoDominioDTO;
import defensoria.pa.def.br.intranet.model.anexo.AnexoDominio;
import defensoria.pa.def.br.intranet.services.anexos.AnexoDominioService;

@RestController
@RequestMapping("/anexoDominio")
public class AnexoDominioController {

    @Autowired
    AnexoDominioService anexoDominioService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/buscarAnexoDominio", params = "nomeAnexoDominio")
    public ResponseEntity<AnexoDominioDTO> getAnexoDominio(@RequestParam String nomeAnexoDominio) {
        AnexoDominio anexoDominio = anexoDominioService.getAnexoDominio(nomeAnexoDominio);
        return ResponseEntity.ok(convertToDTO(anexoDominio));
    }

    @GetMapping(value = "/buscarAnexoDominios")
    public ResponseEntity<List<AnexoDominioDTO>> getAnexoDominios() {
        List<AnexoDominio> anexoDominioList = anexoDominioService.getAllAnexoDominio();
        return ResponseEntity.ok(convertToDTO(anexoDominioList));
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<AnexoDominioDTO> saveAnexoDominio(
            @RequestBody AnexoDominioDTO anexoDominioDTO) {
        AnexoDominio anexoDominio = convertToEntity(anexoDominioDTO);
        anexoDominio = anexoDominioService.saveAnexoDominio(anexoDominio);
        return ResponseEntity.ok(convertToDTO(anexoDominio));
    }

    @PutMapping(value = "/atualizar/")
    public ResponseEntity<AnexoDominioDTO> atualizarAnexoDominio(@RequestBody AnexoDominioDTO anexoDominioDTO) {
        AnexoDominio anexoDominio = convertToEntity(anexoDominioDTO);
        if (anexoDominioService.existsById(anexoDominio.getIdAnexoDominio())) {
            anexoDominio = updateEntityNullValues(anexoDominio);
            anexoDominio = anexoDominioService.saveAnexoDominio(anexoDominio);
            return ResponseEntity.ok(convertToDTO(anexoDominio));
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

    private AnexoDominioDTO convertToDTO(AnexoDominio anexoDominio) {
        AnexoDominioDTO anexoDominioDTO = modelMapper.map(anexoDominio, AnexoDominioDTO.class);
        return anexoDominioDTO;
    }

    private List<AnexoDominioDTO> convertToDTO(List<AnexoDominio> anexoDominioList) {
        List<AnexoDominioDTO> anexoDominioDTO = new ArrayList<>();
        anexoDominioList.forEach(p -> {
            anexoDominioDTO.add(modelMapper.map(p, AnexoDominioDTO.class));
        });
        return anexoDominioDTO;
    }

    private AnexoDominio convertToEntity(AnexoDominioDTO anexoDominioDTO) {
        AnexoDominio anexoDominio = modelMapper.map(anexoDominioDTO, AnexoDominio.class);
        return anexoDominio;
    }

    private AnexoDominio updateEntityNullValues(AnexoDominio src) {
        TypeMap<AnexoDominio, AnexoDominio> typeMap = modelMapper.typeMap(AnexoDominio.class, AnexoDominio.class);
        Provider<AnexoDominio> provider = p -> this.anexoDominioService.getAnexoDominio(src.getIdAnexoDominio());
        typeMap.setProvider(provider);
        AnexoDominio anexoSubCategoria = modelMapper.map(src, AnexoDominio.class);
        return anexoSubCategoria;
    }
}
