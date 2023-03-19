package com.joyful.java.cqrs.core.producer;

import com.joyful.java.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topicName, BaseEvent event);
}
