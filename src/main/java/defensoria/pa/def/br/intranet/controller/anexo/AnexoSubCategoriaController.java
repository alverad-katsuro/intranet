package defensoria.pa.def.br.intranet.controller.anexo;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

import defensoria.pa.def.br.intranet.dto.anexo.request.AnexoSubCategoriaRequestDTO;
import defensoria.pa.def.br.intranet.dto.anexo.response.AnexoSubCategoriaDTO;
import defensoria.pa.def.br.intranet.model.anexo.AnexoSubCategoria;
import defensoria.pa.def.br.intranet.services.anexo.AnexoCategoriaService;
import defensoria.pa.def.br.intranet.services.anexo.AnexoSubCategoriaService;

@RestController
@RequestMapping("/anexoSubCategoria")
public class AnexoSubCategoriaController {

    @Autowired
    AnexoSubCategoriaService anexoSubCategoriaService;

    @Autowired
    AnexoCategoriaService anexoCategoriaService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/consultarAnexoSubCategoria", params = "nomeAnexoSubCategoria")
    public ResponseEntity<AnexoSubCategoriaDTO> getAnexoSubCategoria(@RequestParam String nomeAnexoSubCategoria) {
        AnexoSubCategoria anexoSubCategoria = anexoSubCategoriaService.getAnexoSubCategoria(nomeAnexoSubCategoria);
        return ResponseEntity.ok(convertToDTO(anexoSubCategoria));
    }

    @GetMapping(value = "/consultarAnexoSubCategoriasAtivos", params = {"nomeAnexoCategoria"})
    public ResponseEntity<List<AnexoSubCategoriaDTO>> getAnexoSubCategorias(@RequestParam String nomeAnexoCategoria) {
        List<AnexoSubCategoria> anexoSubCategoriaList = anexoSubCategoriaService.getAllAnexoSubCategoriaAtivo(anexoCategoriaService.getAnexoCategoria(nomeAnexoCategoria));
        return ResponseEntity.ok(convertToDTO(anexoSubCategoriaList));
    }

    @GetMapping(value = "/consultarAnexoSubCategoriasAtivos", params = {"idAnexoCategoria"})
    public ResponseEntity<List<AnexoSubCategoriaDTO>> getAnexoSubCategoriasAtivos(@RequestParam Integer idAnexoCategoria) {
        List<AnexoSubCategoria> anexoSubCategoriaList = anexoSubCategoriaService.getAllAnexoSubCategoriaAtivo(anexoCategoriaService.getAnexoCategoria(idAnexoCategoria));
        return ResponseEntity.ok(convertToDTO(anexoSubCategoriaList));
    }
    
    // FAZER BUSCA INATIVO

    @PostMapping(value = "/salvar")
    public ResponseEntity<AnexoSubCategoriaDTO> saveAnexoSubCategoria(
            @RequestBody AnexoSubCategoriaRequestDTO anexoSubCategoriaDTO) {
        AnexoSubCategoria anexoSubCategoria = convertToEntity(anexoSubCategoriaDTO);
        anexoSubCategoria = anexoSubCategoriaService.saveAnexoSubCategoria(anexoSubCategoria);
        return ResponseEntity.ok(convertToDTO(anexoSubCategoria));
    }

    @PutMapping(value = "/atualizar/")
    public ResponseEntity<AnexoSubCategoriaDTO> atualizarAnexoSubCategoria(
            @RequestBody AnexoSubCategoriaRequestDTO anexoSubCategoriaDTO) throws Exception {
        AnexoSubCategoria anexoSubCategoria = convertToEntity(anexoSubCategoriaDTO);
        if (anexoSubCategoriaService.existsById(anexoSubCategoria.getIdAnexoSubCategoria())) {
            if (anexoSubCategoria.getAnexoCategoria() == null) {
                throw new NotFoundException();
            }
            anexoSubCategoria = anexoSubCategoriaService.saveAnexoSubCategoria(anexoSubCategoria);
            return ResponseEntity.ok(convertToDTO(anexoSubCategoria));
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { NotFoundException.class, })
    public String handleValidationExceptions() {
        return "Categoria não encontrado";
    }

    private AnexoSubCategoriaDTO convertToDTO(AnexoSubCategoria anexoSubCategoria) {
        TypeMap<AnexoSubCategoria, AnexoSubCategoriaDTO> typeMap = modelMapper.typeMap(AnexoSubCategoria.class,
                AnexoSubCategoriaDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.<String>map(src -> src.getAnexoCategoria().getNomeAnexoCategoria(),
                    AnexoSubCategoriaDTO::setNomeAnexoCategoria);
            mapper.<String>map(src -> src.getNomeAnexoSubCategoria(), AnexoSubCategoriaDTO::setNomeAnexoSubCategoria);
        });
        return typeMap.map(anexoSubCategoria);
    }

    private List<AnexoSubCategoriaDTO> convertToDTO(List<AnexoSubCategoria> anexoSubCategoriaList) {
        TypeMap<AnexoSubCategoria, AnexoSubCategoriaDTO> typeMap = modelMapper.typeMap(AnexoSubCategoria.class,
                AnexoSubCategoriaDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.<String>map(src -> src.getAnexoCategoria().getNomeAnexoCategoria(),
                    AnexoSubCategoriaDTO::setNomeAnexoCategoria);
        });
        List<AnexoSubCategoriaDTO> anexoSubCategoriaDTO = new ArrayList<>();
        anexoSubCategoriaList.forEach(p -> {
            anexoSubCategoriaDTO.add(typeMap.map(p));
        });
        return anexoSubCategoriaDTO;
    }

    private AnexoSubCategoria convertToEntity(AnexoSubCategoriaRequestDTO anexoSubCategoriaRequestDTO) {
        TypeMap<AnexoSubCategoriaRequestDTO, AnexoSubCategoria> typeMap = modelMapper
                .typeMap(AnexoSubCategoriaRequestDTO.class, AnexoSubCategoria.class);
        if (anexoSubCategoriaRequestDTO.getIdAnexoSubCategoria() != null) {
            Provider<AnexoSubCategoria> provider = p -> this.anexoSubCategoriaService
                    .getAnexoSubCategoria(anexoSubCategoriaRequestDTO.getIdAnexoSubCategoria());
            typeMap.setProvider(provider);
        }
        /* Não sei pq mais tem bug nisso acho que é o ModelMapper */
        //typeMap.addMappings(mapper -> mapper.<AnexoCategoria>map(src -> this.anexoCategoriaService
        //        .getAnexoCategoria(anexoSubCategoriaRequestDTO.getIdAnexoCategoria()),
        //        AnexoSubCategoria::setAnexoCategoria));
        // mapper.<String>map(src -> src.getNomeAnexoSubCategoria(),
        // AnexoSubCategoriaDTO::setNomeAnexoSubCategoria);

        /* Alternativo */
        AnexoSubCategoria anexoSubCategoria = typeMap.map(anexoSubCategoriaRequestDTO);
        anexoSubCategoria.setAnexoCategoria(this.anexoCategoriaService.getAnexoCategoria(anexoSubCategoriaRequestDTO.getIdAnexoCategoria()));
        return anexoSubCategoria;
    }
}
