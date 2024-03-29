package defensoria.pa.def.br.intranet.repository.anexo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import defensoria.pa.def.br.intranet.model.anexo.AnexoDominio;

public interface AnexoDominioRepository extends CrudRepository<AnexoDominio, Integer> {
    Optional<AnexoDominio> findByNomeAnexoDominio(String nomeAnexoDominio);
    List<AnexoDominio> findByNomeAnexoDominioContainsIgnoreCase(String nomeAnexoDominio);
    List<AnexoDominio> findAll();
    List<AnexoDominio> findAllByAtivoTrue();
}
