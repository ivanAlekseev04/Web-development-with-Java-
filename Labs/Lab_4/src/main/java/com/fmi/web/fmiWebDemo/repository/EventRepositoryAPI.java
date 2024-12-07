package com.fmi.web.fmiWebDemo.repository;

import com.fmi.web.fmiWebDemo.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryAPI {

    /**
     * Add event to your DB
     * @param event
     */
    void addEvent(Event event);

    /**
     * Modify event from your DB
     * @param event
     */
    void updateEvent(Event event);

    /**
     * Delete event by id. If there is no element to be deleted then return false;
     * @param id
     * @return if there is element to delete -> true, if not -> false
     */
    boolean deleteEventById(Integer id);

    /**
     * Get event by passed id. If there is no element return Optional empty
     * @param id
     * @return Optional of Racer
     */
    Optional<Event> getEventById(Integer id);

    /**
     * Get all events
     * @return all event in form of List<Event>
     */
    List<Event> getAllEvents();
}

