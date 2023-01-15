package defensoria.pa.def.br.intranet.dto.anexo.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnexoDTO extends RepresentationModel<AnexoDTO> {   
    
    private String tituloAnexo;
    
    private String nomeAnexo;
    
    private String anexoDominio;
    
    private String anexoCategoria;
    
    private String anexoSubCategoria;
    
    private Boolean ativo;
}
