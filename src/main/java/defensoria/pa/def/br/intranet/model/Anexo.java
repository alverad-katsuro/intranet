package defensoria.pa.def.br.intranet.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Anexo")
@EqualsAndHashCode(callSuper=false)
@AuditOverride(forClass = Auditable.class)
public class Anexo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnexo;
    
    @Audited
    @Column(name = "titulo_anexo", nullable = false, unique = false, length = 60)
    private String tituloAnexo;

    @Audited
    @Column(name = "nome_anexo", nullable = false, unique = false, length = 60)
    private String nomeAnexo;

    @Audited
    @Column(name = "pasta_anexo", nullable = false, unique = true, length = 60)
    private String pastaAnexo;

    @Audited
    @Column(name = "categoria_anexo", nullable = false, unique = false, length = 60)
    private String categoriaAnexo;

    @Audited
    @Column(name = "sub_categoria_anexo", nullable = false, unique = false, length = 60)
    private String subCategoriaAnexo;
}
