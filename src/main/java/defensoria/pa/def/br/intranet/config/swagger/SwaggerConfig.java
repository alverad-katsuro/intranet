package defensoria.pa.def.br.intranet.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI ()
                .info(new Info().title("Intranet API")
                .description("API de Relatórios das atividades realizadas na Defensoria Pública do Estado do Pará.")
                .version("v0.0.1")
                .license(new License().name("Defensoria Pública do Estado Pará").url("http://defensoria.pa.def.br/missao.aspx")))
                .externalDocs(new ExternalDocumentation()
                .description("Wiki do Projeto no Gitlab")
                .url("https://gitlab.defensoria.pa.def.br/alfredo.oliveira/intranet/-/wikis/home"));
    }
}
