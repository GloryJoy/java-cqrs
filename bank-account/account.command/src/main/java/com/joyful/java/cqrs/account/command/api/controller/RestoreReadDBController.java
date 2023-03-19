package com.joyful.java.cqrs.account.command.api.controller;

import com.joyful.java.cqrs.account.command.api.commands.RestoreReadDBCommand;
import com.joyful.java.cqrs.account.common.dto.BaseResponse;
import com.joyful.java.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/vi/restore-read-db")
public class RestoreReadDBController {

    private Logger logger = Logger.getLogger(RestoreReadDBController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDB(){
        try {
            commandDispatcher.send(new RestoreReadDBCommand());
            return new ResponseEntity<>(new BaseResponse("Restore read database successfully."), HttpStatus.OK);

        } catch (Exception e){
            String simpleErrorMessage = "We are not able to process your request.";
            logger.log(Level.SEVERE, MessageFormat.format("Internal Server Error as the following message \n {0}", e));

            return new ResponseEntity<>(new BaseResponse(simpleErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
