package defensoria.pa.def.br.intranet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import defensoria.pa.def.br.intranet.security.AuditorAwareImp;
import defensoria.pa.def.br.intranet.util.AnexoProperties;
import defensoria.pa.def.br.intranet.util.RsaKeyProperties;

@EnableJpaAuditing(auditorAwareRef="auditorAware")
@EnableConfigurationProperties({RsaKeyProperties.class, AnexoProperties.class})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class IntranetApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntranetApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();	
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		return modelMapper;
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImp();
	}

}
