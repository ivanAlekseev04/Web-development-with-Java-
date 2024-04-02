package com.fmi.web.fmiWebDemo.repository;

import com.fmi.web.fmiWebDemo.entity.Event;
import com.fmi.web.fmiWebDemo.exceptions.AlreadyExistedException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class EventRepository implements EventRepositoryAPI {
    private Map<Integer, Event> eventTable = new HashMap<>();
    private static int counter;

    @Override
    public void addEvent(Event event) {
        if (event.getId() != null) {
            throw new AlreadyExistedException(String.format("Event with id %s is already in DB", event.getId()));
        }

        event.setId(counter++);
        eventTable.put(event.getId(), event);
    }

    @Override
    public void updateEvent(Event event) {
        if (!eventTable.containsKey(event.getId())) {
            throw new NoSuchElementException(String.format("Event with id %s is not already" +
                    " in DB to be updated", event.getId()));
        }

        eventTable.replace(event.getId(), event);
    }

    @Override
    public boolean deleteEventById(Integer id) {
        return eventTable.remove(id) != null;
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return Optional.ofNullable(eventTable.get(id));
    }

    @Override
    public List<Event> getAllEvents() {
        return eventTable.values().stream().toList();
    }
}
