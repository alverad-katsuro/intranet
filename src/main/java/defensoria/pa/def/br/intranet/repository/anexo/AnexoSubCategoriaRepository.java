package defensoria.pa.def.br.intranet.repository.anexo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import defensoria.pa.def.br.intranet.model.anexo.AnexoCategoria;
import defensoria.pa.def.br.intranet.model.anexo.AnexoSubCategoria;

public interface AnexoSubCategoriaRepository extends CrudRepository<AnexoSubCategoria, Integer> {
    List<AnexoSubCategoria> findAll();
    Optional<AnexoSubCategoria> findByNomeAnexoSubCategoria(String nomeAnexoSubCategoria);
    List<AnexoSubCategoria> findByAnexoCategoriaAndAtivoTrue(AnexoCategoria anexoCategoria);
    List<AnexoSubCategoria> findAllByAtivoTrue();
}
