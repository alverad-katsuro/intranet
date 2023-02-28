package defensoria.pa.def.br.intranet.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import defensoria.pa.def.br.intranet.dto.auth.AuthenticationRequest;
import defensoria.pa.def.br.intranet.dto.auth.AuthenticationResponse;
import defensoria.pa.def.br.intranet.services.jwt.TokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        return AuthenticationResponse.builder().token(tokenService.generateToken(auth)).build();
    }
    
}
