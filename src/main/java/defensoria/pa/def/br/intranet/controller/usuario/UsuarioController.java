package defensoria.pa.def.br.intranet.controller.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import defensoria.pa.def.br.intranet.model.Usuario;
import defensoria.pa.def.br.intranet.services.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @PostMapping(value="/cadastrar")
    public ResponseEntity<Usuario> postMethodName(@RequestBody @Valid Usuario usuario) {
        usuario = usuarioService.save(usuario);        
        return ResponseEntity.ok(usuario);
    }
    
}
