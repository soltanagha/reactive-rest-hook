package com.task.reactiveresthook.service;

import com.task.reactiveresthook.entity.RESTHookPayload;
import com.task.reactiveresthook.entity.RESTHookTopic;
import com.task.reactiveresthook.entity.Subscription;
import com.task.reactiveresthook.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {


    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Mono<Subscription> getById(String id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public Flux<Subscription> getAllSubscription() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Mono<Boolean> getStatusById(String id) {
        return subscriptionRepository.findById(id).map(subscription ->
                subscription.isActive()
        );
    }

    @Override
    public void subscribe(Subscription subscription) {
        subscriptionRepository
                .findByTopicEqualsAndUrl(subscription.getTopic(), subscription.getUrl()).hasElements()
                .flatMap(hasElements -> {
                    if (!hasElements) {
                        return subscriptionRepository.save(subscription);
                    }
                    return Mono.empty();
                })
                .subscribe();

    }

    @Override
    public void unsubscribe(Subscription subscription) {
        subscriptionRepository
                .findByTopicEqualsAndUrl(subscription.getTopic(), subscription.getUrl())
                .flatMap(subs -> {
                    if (subs.getUrl().equals(subscription.getUrl())) {
                        return subscriptionRepository.delete(subs);
                    }
                    return Mono.empty();
                })
                .subscribe();

    }

    @Override
    public void publishEvent(RESTHookTopic event, Object data) {
        subscriptionRepository
                .findByTopicEquals(event)
                .flatMap(webhook -> {
                    RESTHookPayload payload = new RESTHookPayload(event, data);
                    WebClient client = WebClient.create(webhook.getUrl());
                    client.post()
                            .bodyValue(payload.getData())
                            .retrieve()
                            .bodyToMono(Void.class)
                            .subscribe();
                    return Mono.empty();
                }).subscribe();
    }

    @Override
    public void handleEvent(RESTHookTopic event, Object data) {
        Flux<Subscription> webhooks = subscriptionRepository.findByTopicEquals(event);
        if (webhooks != null) {
            webhooks.flatMap(webhook -> {
                WebClient client = WebClient.create(webhook.getUrl());
                client.post()
                        .bodyValue(data)
                        .retrieve()
                        .bodyToMono(Void.class)
                        .subscribe();
                return null;
            });
        }
    }
}
