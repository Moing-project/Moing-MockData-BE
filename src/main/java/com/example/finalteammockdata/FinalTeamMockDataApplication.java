package com.example.finalteammockdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FinalTeamMockDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalTeamMockDataApplication.class, args);
    }

}
