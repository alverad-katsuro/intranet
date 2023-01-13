package defensoria.pa.def.br.intranet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import defensoria.pa.def.br.intranet.security.SecurityAuditorAware;

@EnableJpaAuditing(auditorAwareRef="auditorAware")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class IntranetApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntranetApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SecurityAuditorAware();
	}

}
