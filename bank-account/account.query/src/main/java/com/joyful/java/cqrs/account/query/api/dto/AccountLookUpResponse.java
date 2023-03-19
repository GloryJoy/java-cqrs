package com.joyful.java.cqrs.account.query.api.dto;

import com.joyful.java.cqrs.account.common.dto.BaseResponse;
import com.joyful.java.cqrs.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLookUpResponse extends BaseResponse  {
    private List<BankAccount> accounts;
    public AccountLookUpResponse(String message){
        super(message);
    }
}
