package defensoria.pa.def.br.intranet.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import defensoria.pa.def.br.intranet.model.Anexo;
import defensoria.pa.def.br.intranet.repository.AnexoRepository;

@Service("AnexoService")
public class AnexoService {
    
    @Autowired
    private AnexoRepository anexoRepository;

    
    @Value("${env.anexosDir}")
    private String UPLOAD_DIRECTORY;

    public Anexo saveAnexo(Anexo anexo, MultipartFile arquivo) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY + "/" + anexo.getPastaAnexo());
          
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
 
        String fileCode = RandomStringUtils.randomAlphanumeric(16);
        String[] nomeAnexoOriginal = arquivo.getOriginalFilename().split("\\.");
        anexo.setNomeAnexo(fileCode +"."+ nomeAnexoOriginal[nomeAnexoOriginal.length - 1]);
         
        try (InputStream inputStream = arquivo.getInputStream()) {
            Path filePath = uploadPath.resolve(anexo.getNomeAnexo());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {       
            throw new IOException("Could not save file: " + arquivo.getOriginalFilename(), ioe);
        }

        anexo = saveAnexo(anexo);
         
        return anexo;
    }

    public Anexo updateAnexo(Anexo anexo, MultipartFile arquivo) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY + "/" + anexo.getPastaAnexo());         
        try (InputStream inputStream = arquivo.getInputStream()) {
            Path filePath = uploadPath.resolve(anexo.getNomeAnexo());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {       
            throw new IOException("Could not update file: " + arquivo.getOriginalFilename(), ioe);
        }
        anexo = saveAnexo(anexo);
        return anexo;
    }

    public Anexo saveAnexo(Anexo anexo){
        return anexoRepository.save(anexo);
    }

    public Iterable<Anexo> getAllAnexo(){
        return anexoRepository.findAll();
    }

    public String selectTituloAnexoByNomeAnexo(String nomeAnexo){
        Optional<String> tituloAnexo = anexoRepository.selectTituloAnexoByNomeAnexo(nomeAnexo);
        return tituloAnexo.orElse(null);
    }

    public Anexo getAnexo(int anexoId){
        return anexoRepository.findById(anexoId).get();
    }

    public List<Anexo> findByPastaAnexo(String pastaAnexo){
        return anexoRepository.findByPastaAnexo(pastaAnexo);
    }

    public boolean existsById(int anexoId){
        return anexoRepository.existsById(anexoId);
    }

    public Resource getFileAsResource(String nomeAnexo) throws MalformedURLException, FileNotFoundException{
        Anexo anexo = anexoRepository.findByNomeAnexo(nomeAnexo).orElse(null);
        return getFileAsResource(anexo);
    }

    public Resource getFileAsResource(Anexo anexo) throws MalformedURLException, FileNotFoundException {
        Path file = Paths.get(String.format("%s/%s/%s", UPLOAD_DIRECTORY, anexo.getPastaAnexo(), anexo.getNomeAnexo()));
        if (file != null) {
            return new UrlResource(file.toUri());
        } else {
            throw new FileNotFoundException(anexo.getTituloAnexo());
        }
    }    
}
