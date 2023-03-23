package com.task.reactiveresthook.service;

import com.task.reactiveresthook.entity.RESTHookTopic;
import com.task.reactiveresthook.entity.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriptionService {

    Mono<Subscription> getById(String id);

    Flux<Subscription> getAllSubscription();

    Mono<Boolean> getStatusById(String id);
    void subscribe(Subscription subscription);
    void unsubscribe(Subscription subscription);

    void publishEvent(RESTHookTopic event, Object data);

    void handleEvent(RESTHookTopic event, Object data);
}
