package defensoria.pa.def.br.intranet.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class SecurityAuditorAware implements AuditorAware<String> {

    private static final String SYSTEM = "System";

    @Override
    public Optional<String> getCurrentAuditor() {
      return Optional.of(SYSTEM);
    }

}
