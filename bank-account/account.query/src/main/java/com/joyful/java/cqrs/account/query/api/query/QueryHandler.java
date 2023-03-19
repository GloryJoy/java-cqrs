package com.joyful.java.cqrs.account.query.api.query;

import com.joyful.java.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountQuery query);
    List<BaseEntity> handle(FindAccountByHolderQuery query);
    List<BaseEntity> handle(FindAccountWithBalanceQuery query);
    List<BaseEntity> handle(FindAccountByIdQuery query);
}
