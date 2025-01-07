package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.EventDTO;
import com.fmi.raceeventmanagement.dto.RacerDTO;
import com.fmi.raceeventmanagement.model.Event;
import com.fmi.raceeventmanagement.model.Team;

import java.time.LocalDateTime;
import java.util.List;

public interface EventServiceAPI {
    List<EventDTO> getAllEvents();

    List<RacerDTO> getAllRacersByTrackName(String trackName);

    Event createEvent(String name, Integer trackId, LocalDateTime dateOfEvent);

    void updateEvent(Event event);

    void deleteEventById(Integer id);

    List<EventDTO> getAllEventsBefore(LocalDateTime date);

    List<EventDTO> getAllEventsAfter(LocalDateTime date);

    List<EventDTO> getUpcomingEvents();

    /**
     * Get all racers from the nearest event (timely manner)
     * @return List of all racers
     */
    List<RacerDTO> getAllRacersForNearestEvent();

    void addTeamToEvent(Integer eventId, Team team);

    void deleteTeamFromEventByName(Integer eventId, String name);

    List<EventDTO> getEventsForTrack(Integer trackId);
}

