package defensoria.pa.def.br.intranet.intranet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import defensoria.pa.def.br.intranet.intranet.model.Anexo;

public interface AnexoRepository extends CrudRepository<Anexo, Integer> {
    Optional<Anexo> findByNomeAnexo(String nomeAnexo);

    List<Anexo> findByPastaAnexo(String pastaAnexo);
}
