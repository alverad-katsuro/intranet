package defensoria.pa.def.br.intranet.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "env")
public class AnexoProperties {
    /**
     * Pasta onde serão armazenados os arquivos a serem disponibilizados pela api.
     **/
    private String anexosDir;

    public String getAnexosDir() {
        return anexosDir;
    }

    public void setAnexosDir(String anexosDir) {
        this.anexosDir = anexosDir;
    }

    
}
