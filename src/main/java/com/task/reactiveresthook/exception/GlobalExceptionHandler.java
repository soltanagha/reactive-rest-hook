package com.task.reactiveresthook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {WebClientResponseException.class})
    public Mono<ResponseEntity<String>> handleWebClientResponseException(WebClientResponseException ex) {
        HttpStatusCode status = ex.getStatusCode();
        String errorMessage = ex.getMessage();
        return Mono.just(ResponseEntity.status(status).body(errorMessage));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<String>> handleResponseStatusException(ResponseStatusException ex) {
        if (ex.getStatusCode() == HttpStatus.METHOD_NOT_ALLOWED) {
            String errorMessage = "Method not allowed for this resource.";
            return Mono.just(ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorMessage));
        } else {
            // Handle other response status exceptions here
            // ...
            return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage()));

        }

    }

}
