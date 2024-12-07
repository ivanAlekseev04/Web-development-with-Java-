package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Event;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.exceptions.AlreadyExistedException;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.EventRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class EventService implements EventServiceAPI {
    @Value("${config.event.upcoming-threshold-days}")
    Integer upcomingThresholdDays;
    @Autowired
    ILogger logger;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TrackRepository trackRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    @Override
    public List<Racer> getAllRacersByTrackName(String trackName) {
        return eventRepository.getAllEvents()
                .stream()
                .filter(e -> Objects.equals(e.getTrack().getName(), trackName))
                .flatMap(e -> e.getTeams()
                        .stream()
                        .flatMap(t -> t.getRacers().stream()))
                .toList();
    }

    @Override
    public Event createEvent(String name, Track track, List<Team> teams, LocalDateTime dateOfEvent) {
        logger.info(String.format("Event '%s' was created", name));
        return eventRepository.addEvent(new Event(name, track, teams, dateOfEvent));
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.updateEvent(event);
    }

    public List<Team> addTeamToEvent(Integer eventId, Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

        if (eventRepository.getEventById(eventId).isEmpty()) {
            throw new NoSuchElementException(String.format("Event with id %s is not in DB", eventId));
        }
        /*
        else if (teamRepository.getTeamByName(team.getName()).isEmpty()) {
            throw new NoSuchElementException(String.format("Team name %s is not in DB",
                    team.getName() == null ? "null" : team.getName()));
        } else if (eventRepository.getEventById(eventId).get()
                .getTeams()
                .stream()
                .anyMatch(t -> Objects.equals(t.getName(), team.getName()))) {

            throw new AlreadyExistedException(String.format("Team with specified name %s already exists in event %s",
                    team.getName(), eventId));
        }
        */

        eventRepository.getEventById(eventId).get().getTeams().add(team);

        return eventRepository.getAllEvents()
                .stream()
                .map(e -> e.getTeams())
                .flatMap(t -> t.stream())
                .toList();
    }

    public boolean deleteTeamFromEventByName(Integer eventId, String name) {
        if (eventRepository.getEventById(eventId).isEmpty()) {
            throw new NoSuchElementException(String.format("Event with id %s is not in DB", eventId));
        }
        /*
        else if (teamRepository.getTeamByName(name).isEmpty()) {
            throw new NoSuchElementException(String.format("Team name %s is not in DB", name));
        }
        */

        return eventRepository.getEventById(eventId).get()
                .getTeams()
                .removeIf(t -> Objects.equals(t.getName(), name));
    }

    @Override
    public boolean deleteEventById(Integer id) {
        return eventRepository.deleteEventById(id);
    }

    @Override
    public List<Event> getAllEventsBefore(LocalDateTime date) {
        return eventRepository.getAllEvents()
                .stream()
                .filter(e -> e.getDateOfEvent().isBefore(date))
                .toList();
    }

    @Override
    public List<Event> getAllEventsAfter(LocalDateTime date) {
        return eventRepository.getAllEvents()
                .stream()
                .filter(e -> e.getDateOfEvent().isAfter(date))
                .toList();
    }

    @Override
    public List<Event> getUpcomingEvents() {
        // Assume that "upcoming event" is event on a current date or on a future date
        return eventRepository.getAllEvents()
                .stream()
                .filter(e -> !e.getDateOfEvent().isBefore(LocalDateTime.now())
                        && e.getDateOfEvent().isBefore(LocalDateTime.now().plusDays(upcomingThresholdDays)))
                .toList();
    }

    @Override
    public List<Racer> getAllRacersForNearestEvent() {
        return eventRepository.getAllEvents()
                .stream()
                .sorted(Comparator.comparingLong(f -> ChronoUnit.SECONDS.between(LocalDateTime.now(), f.getDateOfEvent())))
                .findFirst()
                .map(e -> e.getTeams()
                        .stream()
                        .flatMap(t -> t.getRacers().stream()))
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Event> getEventsForTrack(Track track) {
        return eventRepository.getAllEvents()
                .stream()
                .filter(e -> Objects.equals(e.getTrack().getId(), track.getId()))
                .toList();
    }
}
