package com.joyful.java.cqrs.core.infrastructure;

import com.joyful.java.cqrs.core.domain.BaseEntity;
import com.joyful.java.cqrs.core.query.BaseQuery;
import com.joyful.java.cqrs.core.query.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
