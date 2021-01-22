package no.wangnilsen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FoodRestApiApp
 */
@SpringBootApplication(scanBasePackages = "no.wangnilsen")
public class FoodRestApiApp {
    /**
     * @param args -
     */
    public static void main(final String[] args) {
        SpringApplication.run(FoodRestApiApp.class, args);
    }
}
