package defensoria.pa.def.br.intranet.dto.anexo.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnexoSubCategoriaDTO extends RepresentationModel<AnexoSubCategoriaDTO> {
    private Integer idAnexoSubCategoria;
    private String nomeAnexoCategoria;
    private String nomeAnexoSubCategoria;
    private Boolean ativo;
}
