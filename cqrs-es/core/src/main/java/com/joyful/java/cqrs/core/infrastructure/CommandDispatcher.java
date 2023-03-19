package com.joyful.java.cqrs.core.infrastructure;

import com.joyful.java.cqrs.core.commands.BaseCommand;
import com.joyful.java.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
