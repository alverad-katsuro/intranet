package defensoria.pa.def.br.intranet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import defensoria.pa.def.br.intranet.model.Anexo;

public interface AnexoRepository extends CrudRepository<Anexo, Integer> {
    Optional<Anexo> findByNomeAnexo(String nomeAnexo);

    @Query(value = "Select tituloAnexo from Anexo where nomeAnexo = :nomeAnexo")
    Optional<String> selectTituloAnexoByNomeAnexo(@Param("nomeAnexo") String nomeAnexo);

    List<Anexo> findByPastaAnexo(String pastaAnexo);
}
