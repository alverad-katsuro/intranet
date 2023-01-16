package defensoria.pa.def.br.intranet.services.anexo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import defensoria.pa.def.br.intranet.model.anexo.AnexoSubCategoria;
import defensoria.pa.def.br.intranet.repository.anexo.AnexoSubCategoriaRepository;

@Service("AnexoSubCategoriaService")
public class AnexoSubCategoriaService {

    @Autowired
    AnexoSubCategoriaRepository anexoSubCategoriaRepository;

    public AnexoSubCategoria getAnexoSubCategoria(String nomeAnexoSubCategoria) {
        return anexoSubCategoriaRepository.findByNomeAnexoSubCategoria(nomeAnexoSubCategoria).orElse(null);
    }

    public AnexoSubCategoria saveAnexoSubCategoria(AnexoSubCategoria anexoSubCategoria) {
        return anexoSubCategoriaRepository.save(anexoSubCategoria);
    }

    public boolean existsById(Integer idAnexoSubCategoria) {
        return anexoSubCategoriaRepository.existsById(idAnexoSubCategoria);
    }

    public AnexoSubCategoria getAnexoSubCategoria(Integer idAnexoSubCategoria) {
        return anexoSubCategoriaRepository.findById(idAnexoSubCategoria).orElse(null);
    }

    public List<AnexoSubCategoria> getAllAnexoSubCategoria() {
        return anexoSubCategoriaRepository.findAll();
    }

}
