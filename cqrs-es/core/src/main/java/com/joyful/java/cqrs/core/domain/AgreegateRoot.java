package com.joyful.java.cqrs.core.domain;

import com.joyful.java.cqrs.core.events.BaseEvent;
import org.springframework.boot.logging.LogLevel;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AgreegateRoot {
    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();
    private Logger logger = Logger.getLogger(AgreegateRoot.class.getName());

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted(){
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, Boolean isNewEvent){
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggreage for {0}", event.getClass().getName()));
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, MessageFormat.format("Error applying event to aggregate: {0}", e));
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }
    public void raiseEvent(BaseEvent event){
        applyChange(event, true);
    }

    public void replayEvent(Iterable<BaseEvent> events){
        events.forEach(event -> applyChange(event, false));
    }
}
