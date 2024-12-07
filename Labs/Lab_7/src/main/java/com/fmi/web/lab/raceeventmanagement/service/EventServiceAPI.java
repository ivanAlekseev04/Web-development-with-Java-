package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Event;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EventServiceAPI {
    List<Event> getAllEvents();

    List<Racer> getAllRacersByTrackName(String trackName);

    Event createEvent(String name, Track track, List<Team> teams, LocalDateTime dateOfEvent);

    Event updateEvent(Event event);

    boolean deleteEventById(Integer id);

    List<Event> getAllEventsBefore(LocalDateTime date);

    List<Event> getAllEventsAfter(LocalDateTime date);

    List<Event> getUpcomingEvents();

    /**
     * Get all racers from the nearest event (timely manner)
     * @return List of all racers
     */
    List<Racer> getAllRacersForNearestEvent();

    List<Event> getEventsForTrack(Track track);
}
