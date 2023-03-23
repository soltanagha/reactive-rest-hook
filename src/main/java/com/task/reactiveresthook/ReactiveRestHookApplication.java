package com.task.reactiveresthook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories
public class ReactiveRestHookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveRestHookApplication.class, args);
    }

}
