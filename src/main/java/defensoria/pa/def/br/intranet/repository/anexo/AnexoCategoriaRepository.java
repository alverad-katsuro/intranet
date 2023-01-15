package defensoria.pa.def.br.intranet.repository.anexo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import defensoria.pa.def.br.intranet.model.anexo.AnexoCategoria;

public interface AnexoCategoriaRepository extends CrudRepository<AnexoCategoria, Integer> {
    List<AnexoCategoria> findAll();
    Optional<AnexoCategoria> findByNomeAnexoCategoria(String nomeAnexoCategoria);
}
