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
import com.fmi.web.lab.raceeventmanagement.util.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
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
    public void createEvent(String name, Track track, List<Team> teams,LocalDateTime dateOfEvent) {
        validateEventAttributes(name, track, dateOfEvent);

        eventRepository.addEvent(new Event(name, track, teams, dateOfEvent));
        logger.info(String.format("Event '%s' was created", name));
    }

    @Override
    public void updateEvent(Event event) {
        validateEventAttributes(event.getName(), event.getTrack(), event.getDateOfEvent());

        eventRepository.updateEvent(event);
    }

    public void addTeamToEvent(Integer eventId, Team team) {
        if (eventId == null || eventId < 0) {
            throw new IllegalArgumentException("Invalid eventId. Id can't be negative or null");
        } else if (team == null) {
            throw new IllegalArgumentException("Team can't be null");
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
    }

    public void deleteTeamFromEventByName(Integer eventId, String name) {
        StringValidator.validate(name, "name");

        if (eventId == null || eventId < 0) {
            throw new IllegalArgumentException("Invalid eventId. Id can't be negative or null");
        }

        if (eventRepository.getEventById(eventId).isEmpty()) {
            throw new NoSuchElementException(String.format("Event with id %s is not in DB", eventId));
        }
        /*
        else if (teamRepository.getTeamByName(name).isEmpty()) {
            throw new NoSuchElementException(String.format("Team name %s is not in DB", name));
        }
        */

        if (!eventRepository.getEventById(eventId).get()
                .getTeams()
                .removeIf(t -> Objects.equals(t.getName(), name))) {

            throw new NoSuchElementException(String.format("Team with specified name %s doesn't exist in event %s",
                    name, eventId));
        }
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
        // "nearest Event" can be treated as Event from the past or future Event
        // ascending order of sorting is default for Comparator.comparing()
        return eventRepository.getAllEvents()
                .stream()
                .sorted(Comparator.comparingLong(f -> ChronoUnit.DAYS.between(LocalDateTime.now(), f.getDateOfEvent())))
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

    private void validateEventAttributes(String name, Track track, LocalDateTime dateOfEvent) {
        StringValidator.validate(name, "name");

        if (dateOfEvent == null/* || dateOfEvent.isBefore(LocalDateTime.now())*/) {
            throw new IllegalArgumentException("Invalid dateOfEvent value. LocalDate need" +
                    " to be not null and after or equal to current date");
        }

        if (track == null || track.getId() == null) {
            throw new IllegalArgumentException("Either Track can't be null or its id can't be null");
        }
        /*
        else if (trackRepository.getTrackById(track.getId()).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no Track added in DB with id %s", track.getId()));
        }
        */
    }
}
