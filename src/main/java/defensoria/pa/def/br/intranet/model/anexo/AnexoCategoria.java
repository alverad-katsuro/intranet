package defensoria.pa.def.br.intranet.model.anexo;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "anexo_categoria")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAnexoCategoria")
public class AnexoCategoria extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_anexo_categoria", nullable = false, unique = true)
    private Integer idAnexoCategoria;

    @Audited
    @Column(name = "nome_anexo_categoria", nullable = false, unique = true)
    private String nomeAnexoCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anexo_dominio", nullable = false)
    private AnexoDominio anexoDominio;

    @OneToMany(mappedBy = "anexoCategoria", fetch = FetchType.LAZY)
    Set<Anexo> anexos = new HashSet<>(0);

    @OneToMany(mappedBy = "anexoCategoria", fetch = FetchType.LAZY)
    private Set<AnexoSubCategoria> anexoSubCategorias;

    public AnexoCategoria(Integer idAnexoCategoria) {
        this.idAnexoCategoria = idAnexoCategoria;
    }
}
