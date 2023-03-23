package com.task.reactiveresthook.repository;

import com.task.reactiveresthook.entity.RESTHookTopic;
import com.task.reactiveresthook.entity.Subscription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface SubscriptionRepository extends ReactiveMongoRepository<Subscription, String> {

    Flux<Subscription> findByTopicEquals(RESTHookTopic restHookTopic);
    Flux<Subscription> findByTopicEqualsAndUrl(RESTHookTopic restHookTopic, String url);
}
