package defensoria.pa.def.br.intranet.model.anexo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import defensoria.pa.def.br.intranet.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "anexo")
@EqualsAndHashCode(callSuper=false)
@AuditOverride(forClass = Auditable.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAnexo")
public class Anexo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anexo", nullable = false, unique = true)
    private Integer idAnexo;
    
    @Audited
    @Column(name = "titulo_anexo", nullable = false, unique = false, length = 60)
    private String tituloAnexo;

    @Audited
    @Column(name = "nome_anexo", nullable = false, unique = true, length = 60)
    private String nomeAnexo; 

    @Audited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anexo_dominio", nullable = false, unique = false)
    private AnexoDominio anexoDominio;

    @Audited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anexo_categoria", nullable = false, unique = false)
    private AnexoCategoria anexoCategoria;

    @Audited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anexo_sub_categoria", nullable = false, unique = false)
    private AnexoSubCategoria anexoSubCategoria;

    @JsonIgnore
    public String getNomeAnexoDominio(){
        return this.anexoDominio.getNomeAnexoDominio();
    }

    @JsonIgnore
    public String getNomeAnexoCategoria(){
        return this.anexoCategoria.getNomeAnexoCategoria();
    }

    @JsonIgnore
    public String getNomeAnexoSubCategoria(){
        return this.anexoSubCategoria.getNomeAnexoSubCategoria();
    }
}
