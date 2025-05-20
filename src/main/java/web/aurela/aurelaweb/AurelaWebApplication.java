package web.aurela.aurelaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
public class AurelaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AurelaWebApplication.class, args);
    }

}
