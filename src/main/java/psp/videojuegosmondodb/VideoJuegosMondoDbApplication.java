package psp.videojuegosmondodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class VideoJuegosMondoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoJuegosMondoDbApplication.class, args);
    }

}
