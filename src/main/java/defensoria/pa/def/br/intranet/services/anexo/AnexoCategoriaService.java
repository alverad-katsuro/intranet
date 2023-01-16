package defensoria.pa.def.br.intranet.services.anexo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import defensoria.pa.def.br.intranet.model.anexo.AnexoCategoria;
import defensoria.pa.def.br.intranet.model.anexo.AnexoDominio;
import defensoria.pa.def.br.intranet.repository.anexo.AnexoCategoriaRepository;

@Service("AnexoCategoriaService")
public class AnexoCategoriaService {

    @Autowired
    AnexoCategoriaRepository anexoCategoriaRepository;

    public AnexoCategoria getAnexoCategoria(Integer idAnexoCategoria) {
        return anexoCategoriaRepository.findById(idAnexoCategoria).orElse(null);
    }

    public AnexoCategoria getAnexoCategoria(String nomeAnexoCategoria) {
        return anexoCategoriaRepository.findByNomeAnexoCategoria(nomeAnexoCategoria).orElse(null);
    }

    public List<AnexoCategoria> getAllAnexoCategoria() {
        return anexoCategoriaRepository.findAll();
    }

    public List<AnexoCategoria> getAllAnexoCategoriaAtivo(AnexoDominio anexoDominio) {
        return anexoCategoriaRepository.findByAnexoDominioAndAtivoTrue(anexoDominio);
    }

    public List<AnexoCategoria> getAllAnexoCategoriaAtivo() {
        return anexoCategoriaRepository.findAllByAtivoTrue();
    }

    public AnexoCategoria saveAnexoCategoria(AnexoCategoria anexoCategoria) {
        return anexoCategoriaRepository.save(anexoCategoria);
    }

    public boolean existsById(Integer idAnexoCategoria) {
        return anexoCategoriaRepository.existsById(idAnexoCategoria);
    }



}
