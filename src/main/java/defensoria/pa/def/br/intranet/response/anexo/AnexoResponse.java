package defensoria.pa.def.br.intranet.response.anexo;

import org.springframework.web.multipart.MultipartFile;

import defensoria.pa.def.br.intranet.dto.anexo.response.AnexoDominioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnexoResponse {   
    private Integer idAnexo;
    
    private String tituloAnexo;
    
    private String nomeAnexo;
    
    private AnexoDominioDTO anexoDominio;
    
    private String categoriaAnexo;
    
    private String subCategoriaAnexo;

    private MultipartFile arquivo;
}