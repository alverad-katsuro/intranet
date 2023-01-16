package defensoria.pa.def.br.intranet.services.anexo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import defensoria.pa.def.br.intranet.model.anexo.Anexo;
import defensoria.pa.def.br.intranet.model.anexo.AnexoDominio;
import defensoria.pa.def.br.intranet.repository.anexo.AnexoRepository;

@Service("AnexoService")
public class AnexoService {

    @Autowired
    AnexoRepository anexoRepository;

    @Value("${env.anexosDir}")
    private String UPLOAD_DIRECTORY;

    public List<Anexo> findByAnexoDominio(AnexoDominio anexoDominio) {
        return anexoRepository.findByAnexoDominio(anexoDominio);
    }

    public Anexo getAnexo(String anexoNome) {
        return anexoRepository.findByNomeAnexo(anexoNome).orElse(null);
    }

    public Resource getFileAsResource(Anexo anexo) throws MalformedURLException, FileNotFoundException {
        Path file = Paths.get(String.format("%s/%s/%s", UPLOAD_DIRECTORY, anexo.getAnexoDominio().getNomeAnexoDominio(), anexo.getNomeAnexo()));
        if (file != null) {
            return new UrlResource(file.toUri());
        } else {
            throw new FileNotFoundException(anexo.getTituloAnexo());
        }
    } 

    public boolean existsById(int anexoId) {
        return anexoRepository.existsById(anexoId);
    }

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

    public Anexo getAnexo(Integer idAnexo) {
        return anexoRepository.findById(idAnexo).orElse(null);
    }

    public Anexo saveAnexo(Anexo anexo) {
        return anexoRepository.save(anexo);
    }

}
