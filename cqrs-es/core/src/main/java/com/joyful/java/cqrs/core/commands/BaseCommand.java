package com.joyful.java.cqrs.core.commands;

import com.joyful.java.cqrs.core.messages.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseCommand extends Message {
    public BaseCommand(String id) {
        super(id);
    }
}
