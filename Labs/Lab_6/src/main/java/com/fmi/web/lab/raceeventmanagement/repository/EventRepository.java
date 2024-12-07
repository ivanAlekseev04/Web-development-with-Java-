package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Event;
import com.fmi.web.lab.raceeventmanagement.exceptions.AlreadyExistedException;
import com.fmi.web.lab.raceeventmanagement.repository.sequence.EventSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class EventRepository {
    private Map<Integer, Event> eventTable = new HashMap<>();

    /**
     * Add event to your DB
     * @param event
     */
    public Event addEvent(Event event) {
        if (event.getId() != null) {
            throw new AlreadyExistedException(String.format("Event with id %s is already in DB", event.getId()));
        }

        event.setId(EventSequence.getNextValue());
        eventTable.put(event.getId(), event);
        return event;
    }

    /**
     * Modify event from your DB
     * @param event
     */
    public Event updateEvent(Event event) {
        if (!eventTable.containsKey(event.getId())) {
            throw new NoSuchElementException(String.format("Event with id %s is not already" +
                    " in DB to be updated", event.getId()));
        }

        Event toModify = eventTable.get(event.getId());

        if (event.getName() != null && !event.getName().isEmpty() && !event.getName().isBlank()) {
            toModify.setName(event.getName());
        }
        if (event.getDateOfEvent() != null) {
            toModify.setDateOfEvent(event.getDateOfEvent());
        }
        if (event.getTrack() != null) {
            toModify.setTrack(event.getTrack());
        }
        if (event.getTeams() != null) {
            toModify.setTeams(event.getTeams());
        }

        return toModify;
    }

    /**
     * Delete event by id. If there is no element to be deleted then return false;
     * @param id
     * @return if there is element to delete -> true, if not -> false
     */
    public boolean deleteEventById(Integer id) {
        return eventTable.remove(id) != null;
    }

    /**
     * Get event by passed id. If there is no element return Optional empty
     * @param id
     * @return Optional of Racer
     */
    public Optional<Event> getEventById(Integer id) {
        return Optional.ofNullable(eventTable.get(id));
    }

    /**
     * Get all events
     * @return all event in form of List<Event>
     */
    public List<Event> getAllEvents() {
        return eventTable.values().stream().toList();
    }
}
