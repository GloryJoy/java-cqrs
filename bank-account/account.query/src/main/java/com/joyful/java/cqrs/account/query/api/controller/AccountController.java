package com.joyful.java.cqrs.account.query.api.controller;

import com.joyful.java.cqrs.account.query.api.dto.AccountLookUpResponse;
import com.joyful.java.cqrs.account.query.api.query.FindAccountByHolderQuery;
import com.joyful.java.cqrs.account.query.api.query.FindAccountByIdQuery;
import com.joyful.java.cqrs.account.query.api.query.FindAllAccountQuery;
import com.joyful.java.cqrs.account.query.domain.BankAccount;
import com.joyful.java.cqrs.core.domain.BaseEntity;
import com.joyful.java.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/vi/bank-account-lookup")
public class AccountController {

    private Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookUpResponse> getAllAccounts() {


        try {

            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());

            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookUpResponse
                    .builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("The numbers of account is {0}", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {
            String safeErrorMessage = "The all accounts list can not be returned.";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<AccountLookUpResponse>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<AccountLookUpResponse> getAccountById(@PathVariable("id") String id) {

        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));

            if (accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse
                    .builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Account query has return {0} records.", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);



        } catch (Exception e){
            String safeErrorMessage = "The data can not be retruned.";
            logger.log(Level.SEVERE, MessageFormat.format("Error occured during account query operations as the following details \n {0}", e));
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("holder/{accountholder}")
    public ResponseEntity<AccountLookUpResponse> getAccountByAccountHolder(@PathVariable("accountholder") String accountHolder){

        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));

            if (accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse
                    .builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Account query has return {0} records.", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);



        } catch (Exception e){
            String safeErrorMessage = "The data can not be retruned.";
            logger.log(Level.SEVERE, MessageFormat.format("Error occured during account query operations as the following details \n {0}", e));
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
