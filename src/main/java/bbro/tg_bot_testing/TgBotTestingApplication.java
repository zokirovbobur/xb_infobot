package bbro.tg_bot_testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TgBotTestingApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TgBotTestingApplication.class, args);
    }

}
