package no.wangnilsen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "no.wangnilsen")
public class FoodRestApiApp {
    public static void main(String[] args) {
        SpringApplication.run(FoodRestApiApp.class, args);
    }
}
