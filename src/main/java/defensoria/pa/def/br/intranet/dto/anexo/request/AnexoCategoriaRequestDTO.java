package defensoria.pa.def.br.intranet.dto.anexo.request;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnexoCategoriaRequestDTO extends RepresentationModel<AnexoCategoriaRequestDTO> {
    private Integer idAnexoCategoria;
    private Integer idAnexoDominio;
    private String nomeAnexoCategoria;
    private Boolean ativo = true;
}
