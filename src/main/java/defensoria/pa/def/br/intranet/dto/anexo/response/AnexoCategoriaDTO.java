package defensoria.pa.def.br.intranet.dto.anexo.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnexoCategoriaDTO extends RepresentationModel<AnexoCategoriaDTO> {
    private Integer idAnexoCategoria;
    private String nomeAnexoCategoria;
    private Boolean ativo;
}
