package defensoria.pa.def.br.intranet.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImp implements AuditorAware<String> {

	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(authentication.getName());
	}

}
