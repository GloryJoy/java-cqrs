package com.joyful.java.cqrs.core.handler;

import com.joyful.java.cqrs.core.domain.AgreegateRoot;

public interface EventSourcingHandler<T> {
    void save(AgreegateRoot agreegate);
    T getById(String id);

    void republishEvents();
}
