package com.joyful.java.cqrs.account.query.api.query;

import com.joyful.java.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {
    private String accountHolder;
}
