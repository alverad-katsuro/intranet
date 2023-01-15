package defensoria.pa.def.br.intranet.repository.anexo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import defensoria.pa.def.br.intranet.model.anexo.Anexo;
import defensoria.pa.def.br.intranet.model.anexo.AnexoDominio;

public interface AnexoRepository extends CrudRepository<Anexo, Integer> {
    Optional<Anexo> findByNomeAnexo(String nomeAnexo);

    @Query(value = "Select tituloAnexo from anexo where nomeAnexo = :nomeAnexo")
    Optional<String> selectTituloAnexoByNomeAnexo(@Param("nomeAnexo") String nomeAnexo);

    //@Query(value = "Select * from anexo where anexoDominio = (select nomeAnexoDominio from AnexoDominio where nomeAnexoDominio = :nomeAnexoDominio)")
    //List<Anexo> findByNomeAnexoDominio(@Param("nomeAnexoDominio") String nomeAnexoDominio);
    List<Anexo> findByAnexoDominio(AnexoDominio anexoDominio);

    List<Anexo> findAll();
}
