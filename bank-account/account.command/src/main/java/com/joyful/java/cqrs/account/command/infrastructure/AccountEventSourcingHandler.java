package com.joyful.java.cqrs.account.command.infrastructure;

import com.joyful.java.cqrs.account.command.domain.AccountAggregate;
import com.joyful.java.cqrs.core.domain.AgreegateRoot;
import com.joyful.java.cqrs.core.handler.EventSourcingHandler;
import com.joyful.java.cqrs.core.infrastructure.EventStore;
import com.joyful.java.cqrs.core.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    EventStore eventStore;

    @Autowired
    EventProducer eventProducer;

    @Override
    public void save(AgreegateRoot agreegate) {
        eventStore.saveEvents(agreegate.getId(), agreegate.getUncommittedChanges(), agreegate.getVersion());
        agreegate.markChangesAsCommitted();

    }

    @Override
    public AccountAggregate getById(String id) {
        AccountAggregate aggregate = new AccountAggregate();

        return Optional
                .ofNullable(eventStore.getEvents(id))
                .map(eventList -> {
                    aggregate.replayEvent(eventList);
                    var latestVersion = eventList
                            .stream()
                            .map(v -> v.getVersion())
                            .max(Comparator.naturalOrder())
                            .get();

                    aggregate.setVersion(latestVersion);
                    return aggregate;
                })
                .get();
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        aggregateIds
                .stream()
                .map(this::getById)
                .filter(accountAggregate -> accountAggregate != null || accountAggregate.getActive())
                .map(accountAggregate -> eventStore.getEvents(accountAggregate.getId()))
                .forEach(events -> events.forEach(event -> {
                    eventProducer.produce(event.getClass().getSimpleName(), event);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

}
