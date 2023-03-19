package com.joyful.java.cqrs.account.query.api.query;

import com.joyful.java.cqrs.account.query.api.dto.EqualityType;
import com.joyful.java.cqrs.account.query.domain.AccountRepository;
import com.joyful.java.cqrs.account.query.domain.BankAccount;
import com.joyful.java.cqrs.core.domain.BaseEntity;
import com.joyful.java.cqrs.core.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountQuery query) {
        Iterable<BankAccount> accounts = accountRepository.findAll();
        List<BaseEntity> accList = new ArrayList<>();
        accounts.forEach(accList::add);

        return accList;

    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) throws AccountNotFoundException {
        var account = accountRepository.findByAccountHolder(query.getAccountHolder());

        return new ArrayList<BaseEntity>(){
            { add(account.orElseThrow(() -> new AccountNotFoundException()));}
        };
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {

        return query.getEqualityType() == EqualityType.GREATER_THAN ?
                accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) throws AccountNotFoundException {
        var account = accountRepository.findById(query.getId());

        return Arrays.asList(account
                .orElseThrow(() -> new AccountNotFoundException()));
    }
}
