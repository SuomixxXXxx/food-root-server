package org.chiches.foodrootservir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FoodRootServirApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodRootServirApplication.class, args);
    }

}
