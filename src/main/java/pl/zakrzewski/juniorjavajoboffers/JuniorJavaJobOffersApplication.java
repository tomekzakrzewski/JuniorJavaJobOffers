package pl.zakrzewski.juniorjavajoboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class JuniorJavaJobOffersApplication {
    public static void main(String[] args) {
        SpringApplication.run(JuniorJavaJobOffersApplication.class, args);
    }
}
