package com.task.reactiveresthook.entity;

public class RESTHookPayload<T> {
    private final RESTHookTopic event;
    private final T data;

    public RESTHookPayload(RESTHookTopic event, T data) {
        this.event = event;
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
