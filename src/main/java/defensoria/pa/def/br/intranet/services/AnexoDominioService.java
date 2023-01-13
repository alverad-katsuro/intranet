package defensoria.pa.def.br.intranet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import defensoria.pa.def.br.intranet.model.AnexoDominio;
import defensoria.pa.def.br.intranet.repository.AnexoDominioRepository;

@Service("AnexoDominioService")
public class AnexoDominioService {
    
    @Autowired
    private AnexoDominioRepository anexoDominioRepository;

    public AnexoDominio saveAnexoDominio(AnexoDominio anexoDominio){
        return anexoDominioRepository.save(anexoDominio);
    }

    public List<AnexoDominio> getAllAnexoDominio(){
        return anexoDominioRepository.findAll();
    }

    public AnexoDominio getAnexoDominio(int anexoId){
        return anexoDominioRepository.findById(anexoId).get();
    }

    public AnexoDominio getAnexoDominio(String nomeAnexoDominio){
        return anexoDominioRepository.findByNomeAnexoDominio(nomeAnexoDominio).orElse(null);
    }

    public boolean existsById(int anexoId){
        return anexoDominioRepository.existsById(anexoId);
    } 
}
