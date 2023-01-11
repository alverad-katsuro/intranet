package defensoria.pa.def.br.intranet.intranet.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Anexo")
public class Anexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnexo;
    @Column(name = "titulo_anexo", nullable = false, unique = false, length = 60)
    private String tituloAnexo;
    @Column(name = "nome_anexo", nullable = false, unique = false, length = 60)
    private String nomeAnexo;
    @Column(name = "pasta_anexo", nullable = false, unique = false, length = 60)
    private String pastaAnexo;
    @Column(name = "categoria_anexo", nullable = false, unique = false, length = 60)
    private String categoriaAnexo;
    @Column(name = "sub_categoria_anexo", nullable = false, unique = false, length = 60)
    private String subCategoriaAnexo;
}
