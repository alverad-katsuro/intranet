package defensoria.pa.def.br.intranet.controller.anexo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import defensoria.pa.def.br.intranet.dto.anexo.request.AnexoRequestDTO;
import defensoria.pa.def.br.intranet.dto.anexo.response.AnexoDTO;
import defensoria.pa.def.br.intranet.model.anexo.Anexo;
import defensoria.pa.def.br.intranet.model.anexo.AnexoCategoria;
import defensoria.pa.def.br.intranet.model.anexo.AnexoDominio;
import defensoria.pa.def.br.intranet.model.anexo.AnexoSubCategoria;
import defensoria.pa.def.br.intranet.services.anexo.AnexoCategoriaService;
import defensoria.pa.def.br.intranet.services.anexo.AnexoDominioService;
import defensoria.pa.def.br.intranet.services.anexo.AnexoService;
import defensoria.pa.def.br.intranet.services.anexo.AnexoSubCategoriaService;

@RestController
@RequestMapping("/anexo")
public class AnexoController {

    @Autowired
    AnexoService anexoService;

    @Autowired
    AnexoDominioService anexoDominioService;

    @Autowired
    AnexoCategoriaService anexoCategoriaService;

    @Autowired
    AnexoSubCategoriaService anexoSubCategoriaService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/buscarAnexos", params = { "nomeAnexoDominio" })
    public ResponseEntity<List<AnexoDTO>> getAnexos(@RequestParam String nomeAnexoDominio) {
        List<Anexo> anexosList = anexoService.findByAnexoDominio(anexoDominioService.getAnexoDominio(nomeAnexoDominio));
        return ResponseEntity.ok(convertToDTO(anexosList));
    }

    @GetMapping(value = "/buscarAnexosAtivos", params = { "nomeAnexoDominio"})
    public ResponseEntity<List<AnexoDTO>> getAnexosAtivos(@RequestParam String nomeAnexoDominio) {
        List<Anexo> anexosList = anexoService.findByAnexoDominioAndAtivoTrue(anexoDominioService.getAnexoDominio(nomeAnexoDominio));
        return ResponseEntity.ok(convertToDTO(anexosList));
    }

    @GetMapping(value = "/buscarAnexo", params = { "anexoNome" })
    public ResponseEntity<AnexoDTO> getAnexo(@RequestParam("anexoNome") String anexoNome)
            throws MalformedURLException, FileNotFoundException {
        Anexo anexo = anexoService.getAnexo(anexoNome);
        return ResponseEntity.ok().body(convertToDTO(anexo));
    }

    @GetMapping(value = "/buscarAnexo", params = { "idAnexo" })
    public ResponseEntity<AnexoDTO> getAnexo(@RequestParam("idAnexo") Integer idAnexo)
            throws MalformedURLException, FileNotFoundException {
        Anexo anexo = anexoService.getAnexo(idAnexo);
        return ResponseEntity.ok().body(convertToDTO(anexo));
    }

    @GetMapping(value = "/downloadAnexo", params = { "anexoNome" })
    public ResponseEntity<Resource> downloadAnexo(@RequestParam("anexoNome") String anexoNome)
            throws MalformedURLException, FileNotFoundException {
        Anexo anexo = anexoService.getAnexo(anexoNome);
        Resource resource = anexoService.getFileAsResource(anexo);
        String contentType = "application/octet-stream";
        String headerValue = String.format("attachment; filename=\"%s.%s\"",
                anexo.getTituloAnexo(), "pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping(value = "/downloadAnexo", params = { "idAnexo" })
    public ResponseEntity<Resource> downloadAnexo(@RequestParam("idAnexo") Integer idAnexo)
            throws MalformedURLException, FileNotFoundException {
        Anexo anexo = anexoService.getAnexo(idAnexo);
        Resource resource = anexoService.getFileAsResource(anexo);
        String contentType = "application/octet-stream";
        String headerValue = String.format("attachment; filename=\"%s.%s\"",
                anexo.getTituloAnexo(), "pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PostMapping(value = "/salvar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AnexoDTO> saveAnexo(@ModelAttribute AnexoRequestDTO anexoDTO) throws Exception {
        Anexo anexo = convertToEntity(anexoDTO);
        anexo = anexoService.saveAnexo(anexo, anexoDTO.getArquivo());
        return ResponseEntity.ok(convertToDTO(anexo));
    }

    @PutMapping(value = "/atualizar/{anexoId}")
    public ResponseEntity<AnexoDTO> atualizarAnexo(@ModelAttribute AnexoRequestDTO anexoDTO,
            @PathVariable int anexoId)
            throws IOException {
        Anexo anexo = convertToEntity(anexoDTO);
        if (anexoService.existsById(anexoId)) {
            if (anexoDTO.getArquivo().isEmpty()) {
                anexo = anexoService.saveAnexo(anexo);
            } else {
                anexo = updateEntityNullValues(anexo);
                anexo = anexoService.updateAnexo(anexo, anexoDTO.getArquivo());
            }
            return ResponseEntity
                    .ok(convertToDTO(anexo).add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(AnexoController.class).downloadAnexo(anexo.getIdAnexo()))
                            .withSelfRel()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { FileNotFoundException.class, MalformedURLException.class })
    public String handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return ex.getMessage();
    }

    private Anexo updateEntityNullValues(Anexo src) {
        TypeMap<Anexo, Anexo> typeMap = modelMapper.typeMap(Anexo.class, Anexo.class);
        Provider<Anexo> provider = p -> this.anexoService.getAnexo(src.getIdAnexo());
        typeMap.setProvider(provider);
        Anexo anexoSubCategoria = modelMapper.map(src, Anexo.class);
        return anexoSubCategoria;
    }

    private List<AnexoDTO> convertToDTO(List<Anexo> anexoList) {
        List<AnexoDTO> anexoCategoriaDTO = new ArrayList<>();
        TypeMap<Anexo, AnexoDTO> typeMap = modelMapper.typeMap(Anexo.class, AnexoDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.<String>map(Anexo::getNomeAnexoDominio, AnexoDTO::setAnexoDominio);
            mapper.<String>map(Anexo::getNomeAnexoCategoria, AnexoDTO::setAnexoCategoria);
            mapper.<String>map(Anexo::getNomeAnexoSubCategoria, AnexoDTO::setAnexoSubCategoria);
        });
        anexoList.forEach(anexo -> {
            try {
                anexoCategoriaDTO.add(typeMap.map(anexo).add(WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(AnexoController.class).downloadAnexo(anexo.getIdAnexo()))
                        .withSelfRel()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        return anexoCategoriaDTO;
    }

    private AnexoDTO convertToDTO(Anexo anexo) throws MalformedURLException, FileNotFoundException {
        TypeMap<Anexo, AnexoDTO> typeMap = modelMapper.typeMap(Anexo.class, AnexoDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.<String>map(Anexo::getNomeAnexoDominio, AnexoDTO::setAnexoDominio);
            mapper.<String>map(Anexo::getNomeAnexoCategoria, AnexoDTO::setAnexoCategoria);
            mapper.<String>map(Anexo::getNomeAnexoSubCategoria, AnexoDTO::setAnexoSubCategoria);
        });
        return typeMap.map(anexo).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(AnexoController.class).downloadAnexo(anexo.getIdAnexo()))
                .withSelfRel());
    }

    private Anexo convertToEntity(AnexoRequestDTO anexoRequestDTO) {
        TypeMap<AnexoRequestDTO, Anexo> typeMap = modelMapper.typeMap(AnexoRequestDTO.class, Anexo.class);
        if (anexoRequestDTO.getIdAnexo() != null) {
            Provider<Anexo> provider = p -> this.anexoService.getAnexo(anexoRequestDTO.getIdAnexo());
            typeMap.setProvider(provider);
        }
        typeMap.addMappings(mapper -> {
            mapper.<AnexoDominio>map(src -> anexoDominioService.getAnexoDominio(anexoRequestDTO.getIdAnexoDominio()),
                    Anexo::setAnexoDominio);
            mapper.<AnexoCategoria>map(
                    src -> this.anexoCategoriaService.getAnexoCategoria(anexoRequestDTO.getIdAnexoCategoria()),
                    Anexo::setAnexoCategoria);
            mapper.<AnexoSubCategoria>map(
                    src -> this.anexoSubCategoriaService.getAnexoSubCategoria(anexoRequestDTO.getIdAnexoSubCategoria()),
                    Anexo::setAnexoSubCategoria);
        });
        Anexo anexo = typeMap.map(anexoRequestDTO);
        return anexo;
    }
}
