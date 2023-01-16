package defensoria.pa.def.br.intranet.dto.anexo.request;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnexoRequestDTO extends RepresentationModel<AnexoRequestDTO> {   
    private Integer idAnexo;
    
    private String tituloAnexo;
        
    private Integer idAnexoDominio;
    
    private Integer idAnexoCategoria;
    
    private Integer idAnexoSubCategoria;

    private Boolean ativo = true;

    private MultipartFile arquivo;
}
