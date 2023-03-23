package com.task.reactiveresthook.controller;

import com.task.reactiveresthook.entity.RESTHookTopic;
import com.task.reactiveresthook.entity.Subscription;
import com.task.reactiveresthook.repository.SubscriptionRepository;
import com.task.reactiveresthook.service.SubscriptionService;
import com.task.reactiveresthook.service.SubscriptionServiceImpl;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionServiceImpl subscriptionService;

    public SubscriptionController(SubscriptionServiceImpl subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public Flux<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscription();
    }

    @GetMapping("{id}")
    public Mono<Subscription> getSubscriptionById(@PathVariable String id) {
        return subscriptionService.getById(id);
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
