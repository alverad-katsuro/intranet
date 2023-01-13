package defensoria.pa.def.br.intranet.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnexoDTO {   
    private Integer idAnexo;
    
    private String tituloAnexo;
    
    private String nomeAnexo;
    
    private String pastaAnexo;
    
    private String categoriaAnexo;
    
    private String subCategoriaAnexo;

    private MultipartFile arquivo;
}
