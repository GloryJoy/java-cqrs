package com.joyful.java.cqrs.account.query.infrastructure;

import com.joyful.java.cqrs.core.domain.BaseEntity;
import com.joyful.java.cqrs.core.exception.QueryHandlerMethodMultipleMethodFoundException;
import com.joyful.java.cqrs.core.exception.QueryHandlerMethodNotFoundException;
import com.joyful.java.cqrs.core.infrastructure.QueryDispatcher;
import com.joyful.java.cqrs.core.query.BaseQuery;
import com.joyful.java.cqrs.core.query.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> route = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = route.computeIfAbsent(type, c -> new LinkedList<>())
                .add(handler);

    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) throws QueryHandlerMethodNotFoundException, QueryHandlerMethodMultipleMethodFoundException {
        return Optional.ofNullable(route.get(query.getClass()))
                .map(handlers -> {
                    if (handlers.size() > 1) throw new QueryHandlerMethodMultipleMethodFoundException();
                    return handlers;
                })
                .orElseThrow(() -> new QueryHandlerMethodNotFoundException())
                .get(0)
                .handle(query);

    }
}

