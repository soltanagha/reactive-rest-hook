package com.task.reactiveresthook.controller;

import com.task.reactiveresthook.entity.RESTHookTopic;
import com.task.reactiveresthook.entity.Response;
import com.task.reactiveresthook.entity.Subscription;
import com.task.reactiveresthook.service.SubscriptionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionServiceImpl subscriptionService;

    public SubscriptionController(SubscriptionServiceImpl subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public Flux<ResponseEntity<Response>> getAllSubscriptions() {
        return Flux.just(ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("subscriptions",subscriptionService.getAllSubscription()))
                        .message("Fetched all subscriptions")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        ));
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Response>> getSubscriptionById(@PathVariable String id) {
        return Mono.just(ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("subscription",subscriptionService.getById(id)))
                        .message("Subscriptions fetched by id")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        ));
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Subscription subscription) {
        subscriptionService.subscribe(subscription);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody Subscription subscription) {
        subscriptionService.unsubscribe(subscription);
    }

    @PostMapping("/publish/{event}")
    public void publishEvent(@PathVariable RESTHookTopic event, @RequestBody Object data) {
        subscriptionService.publishEvent(event, data);
    }

    @PostMapping("/handle/{event}")
    public void handleEvent(@PathVariable RESTHookTopic event, @RequestBody Object data) {
       subscriptionService.handleEvent(event, data);
    }
}
