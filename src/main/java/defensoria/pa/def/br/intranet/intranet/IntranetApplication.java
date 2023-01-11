package defensoria.pa.def.br.intranet.intranet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class IntranetApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntranetApplication.class, args);
	}

}
