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
import defensoria.pa.def.br.intranet.model.AnexoDominio;
import defensoria.pa.def.br.intranet.repository.AnexoDominioRepository;
import defensoria.pa.def.br.intranet.repository.AnexoRepository;

@Service("AnexoService")
public class AnexoService {
    
    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private AnexoDominioRepository anexoDominioRepository;

    
    @Value("${env.anexosDir}")
    private String UPLOAD_DIRECTORY;

    public Anexo saveAnexo(Anexo anexo, MultipartFile arquivo) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY + "/" + anexo.getAnexoDominio().getNomeAnexoDominio());
          
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
 
        String fileCode = RandomStringUtils.randomAlphanumeric(16);
        anexo.setNomeAnexo(fileCode +".pdf");
         
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
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY + "/" + anexo.getAnexoDominio().getNomeAnexoDominio());         
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

    public List<Anexo> getAllAnexo(){
        return anexoRepository.findAll();
    }


    public List<Anexo> findByAnexoDominio(String nomeAnexoDominio){
        return findByAnexoDominio(anexoDominioRepository.findByNomeAnexoDominio(nomeAnexoDominio).orElse(null));
    }


    public List<Anexo> findByAnexoDominio(AnexoDominio anexoDominio){
        return anexoRepository.findByAnexoDominio(anexoDominio);
    }

    public String selectTituloAnexoByNomeAnexo(String nomeAnexo){
        Optional<String> tituloAnexo = anexoRepository.selectTituloAnexoByNomeAnexo(nomeAnexo);
        return tituloAnexo.orElse(null);
    }

    public Anexo getAnexo(int anexoId){
        return anexoRepository.findById(anexoId).get();
    }

    public boolean existsById(int anexoId){
        return anexoRepository.existsById(anexoId);
    }

    public Resource getFileAsResource(String nomeAnexo) throws MalformedURLException, FileNotFoundException{
        Anexo anexo = anexoRepository.findByNomeAnexo(nomeAnexo).orElse(null);
        return getFileAsResource(anexo);
    }

    public Resource getFileAsResource(Anexo anexo) throws MalformedURLException, FileNotFoundException {
        Path file = Paths.get(String.format("%s/%s/%s", UPLOAD_DIRECTORY, anexo.getAnexoDominio().getNomeAnexoDominio(), anexo.getNomeAnexo()));
        if (file != null) {
            return new UrlResource(file.toUri());
        } else {
            throw new FileNotFoundException(anexo.getTituloAnexo());
        }
    }    
}
