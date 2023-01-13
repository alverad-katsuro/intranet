package defensoria.pa.def.br.intranet.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class AnexoDominio extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anexo_dominio", nullable = false, unique = true)
    private Integer idAnexoDominio;

    @Audited
    @Column(name = "nome_anexo_dominio", nullable = false, unique = true)
    private String nomeAnexoDominio;

    @OneToMany(mappedBy = "anexoDominio", fetch = FetchType.LAZY)
    Set<Anexo> anexos = new HashSet<>(0);
}
