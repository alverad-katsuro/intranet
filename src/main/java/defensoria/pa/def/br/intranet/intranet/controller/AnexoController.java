package defensoria.pa.def.br.intranet.intranet.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import defensoria.pa.def.br.intranet.intranet.model.Anexo;



@RestController
@RequestMapping("/anexos")
public class AnexoController {
    
    @GetMapping(value="/")
    public String home() {
        return "oi";
    }

    @PostMapping(value="/salvar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Anexo postMethodName(@RequestPart Anexo entity, @RequestPart MultipartFile file) {
        
        
        return null;
    }
    
    
}
