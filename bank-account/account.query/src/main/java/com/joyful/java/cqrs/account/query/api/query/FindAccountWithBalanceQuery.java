package com.joyful.java.cqrs.account.query.api.query;

import com.joyful.java.cqrs.account.query.api.dto.EqualityType;
import com.joyful.java.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private double balance;
    private EqualityType equalityType;
}
