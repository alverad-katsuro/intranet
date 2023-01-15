package defensoria.pa.def.br.intranet.dto.anexo.request;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnexoSubCategoriaRequestDTO extends RepresentationModel<AnexoSubCategoriaRequestDTO> {
    private Integer idAnexoCategoria;
    private Integer idAnexoSubCategoria;
    private String nomeAnexoSubCategoria;
    private Boolean ativo = true;
}
