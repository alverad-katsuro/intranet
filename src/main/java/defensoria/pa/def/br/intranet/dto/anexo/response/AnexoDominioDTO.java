package defensoria.pa.def.br.intranet.dto.anexo.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnexoDominioDTO extends RepresentationModel<AnexoDominioDTO> {
    private Integer idAnexoDominio;
    private String nomeAnexoDominio;
    private Boolean ativo;
}
