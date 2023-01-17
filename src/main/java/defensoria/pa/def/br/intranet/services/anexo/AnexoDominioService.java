package defensoria.pa.def.br.intranet.services.anexo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import defensoria.pa.def.br.intranet.model.anexo.AnexoDominio;
import defensoria.pa.def.br.intranet.repository.anexo.AnexoDominioRepository;

@Service("AnexoDominioService")
public class AnexoDominioService {

    @Autowired
    AnexoDominioRepository anexoDominioRepository;

    public AnexoDominio getAnexoDominio(String nomeAnexoDominio) {
        return anexoDominioRepository.findByNomeAnexoDominio(nomeAnexoDominio).orElse(null);
    }

    public List<AnexoDominio> getByNomeAnexoDominioContainsIgnoreCase(String nomeAnexoDominio) {
        return anexoDominioRepository.findByNomeAnexoDominioContainsIgnoreCase(nomeAnexoDominio);
    }

    public List<AnexoDominio> getAllAnexoDominio() {
        return anexoDominioRepository.findAll();
    }

    public List<AnexoDominio> getAllAnexoDominioAtivo() {
        return anexoDominioRepository.findAllByAtivoTrue();
    }

    public AnexoDominio saveAnexoDominio(AnexoDominio anexoDominio) {
        return anexoDominioRepository.save(anexoDominio);
    }

    public boolean existsById(Integer idAnexoDominio) {
        return anexoDominioRepository.existsById(idAnexoDominio);
    }

    public AnexoDominio getAnexoDominio(Integer idAnexoDominio) {
        return anexoDominioRepository.findById(idAnexoDominio).orElse(null);
    }

}
