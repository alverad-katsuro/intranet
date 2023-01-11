package defensoria.pa.def.br.intranet.intranet.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import defensoria.pa.def.br.intranet.intranet.model.Anexo;
import defensoria.pa.def.br.intranet.intranet.repository.AnexoRepository;

@Service("AnexoService")
public class AnexoService {
    
    @Autowired
    private AnexoRepository anexoRepository;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/anexos/";

    @Transactional
    public Anexo saveAnexo(Anexo anexo, MultipartFile file){
        //anexo.setPastaAnexo(UPLOAD_DIRECTORY + "/" + anexo.getPastaAnexo());
        if (file != null) {
            try {
                if (Files.deleteIfExists(new File(UPLOAD_DIRECTORY + anexo.getNomeAnexo()).toPath())) {
                    System.out.println("ARQUIVO DELETADO");
                } else {
                    System.out.println("ARQUIVO NÂO DELETADO");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folder = new File(UPLOAD_DIRECTORY);
            if (!folder.exists()) {
                folder.mkdir();
            }
            String novo_nome;
            Path path;
            do {
                novo_nome = UUID.randomUUID().toString() + ".png";
                path = Paths.get(UPLOAD_DIRECTORY, novo_nome);
                System.out.println(path.toString());
            
            } while(path.toFile().isFile());
            
            anexo.setNomeAnexo(novo_nome);
            anexo = saveAnexo(anexo);
            if (anexo.getIdAnexo() != 0) {
                try {
                    Files.write(path, file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }                
            }
        } else {
            anexo = saveAnexo(anexo);
        }
        return anexo;
    }

    @Transactional
    public Anexo saveAnexo(Anexo anexo){
        return anexoRepository.save(anexo);
    }

    @Transactional
    public Iterable<Anexo> getAllAnexo(){
        return anexoRepository.findAll();
    }

    @Transactional
    public Anexo getAnexo(int anexoId){
        return anexoRepository.findById(anexoId).get();
    }

    @Transactional
    public void deleteAnexo(Anexo anexo) {
        anexoRepository.delete(anexo);
    }

    // @Transactional
    // public Iterable<Anexo> getAnexoByCategoria(int categoriaId){
    //     return anexoRepository.findByCategoria(categoriaId);
    // }
}
