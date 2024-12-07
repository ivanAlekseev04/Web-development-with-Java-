package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Event;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.EventRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Service
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
        return eventRepository.findAll();
    }

    @Override
    public List<Racer> getAllRacersByTrackName(String trackName) {
        return eventRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getTrack().getName(), trackName))
                .flatMap(e -> e.getTeams()
                        .stream()
                        .flatMap(t -> t.getRacers().stream()))
                .toList();
    }

    @Override
    public Event createEvent(String name, Track track, Set<Team> teams, LocalDateTime dateOfEvent) {
        var actualTrack = trackRepository.findById(track.getId());

        if(actualTrack.isEmpty()) {
            throw new EntityNotFoundException(String.format("Track with id %s is not" +
                    " already in DB", track.getId()));
        }

        Event toCreate = new Event(name, actualTrack.get(), teams, dateOfEvent);

        if(Set.of(teamRepository.findAll()).contains(toCreate)) {
            throw new DataIntegrityViolationException(String.format("Event with name %s on track %s on a date %s " +
                    "is already existed", name, track.toString(), dateOfEvent.toString()));
        }

        logger.info(String.format("Event '%s' was created", name));
        Event newEvent = eventRepository.save(toCreate);
        teamRepository.assignEventToTeams(newEvent, teams);
        //trackRepository.assignEventToTrack(newEvent, track);

        return newEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.update(event, trackRepository, teamRepository);
    }

    public Set<Team> addTeamToEvent(Integer eventId, Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

        var toUpdate = eventRepository.findById(eventId);

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %s is not in DB", eventId));
        }

        teamRepository.assignEventToTeams(toUpdate.get(), Set.of(team));

        return eventRepository.findById(eventId).get().getTeams();
    }

    public Set<Team> deleteTeamFromEventByName(Integer eventId, String name) {
        var toUpdate = eventRepository.findById(eventId);

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %s is not in DB", eventId));
        }

        teamRepository.findById(name).ifPresent(t -> {
            t.getEvents().remove(toUpdate.get());
            teamRepository.save(t);
        });

        return toUpdate.get().getTeams();
    }

    @Override
    public void deleteEventById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> getAllEventsBefore(LocalDateTime date) {
        return eventRepository.findAll()
                .stream()
                .filter(e -> e.getDateOfEvent().isBefore(date))
                .toList();
    }

    @Override
    public List<Event> getAllEventsAfter(LocalDateTime date) {
        return eventRepository.findAll()
                .stream()
                .filter(e -> e.getDateOfEvent().isAfter(date))
                .toList();
    }

    @Override
    public List<Event> getUpcomingEvents() {
        // Assume that "upcoming event" is event on a current date or on a future date
        return eventRepository.findAll()
                .stream()
                .filter(e -> !e.getDateOfEvent().isBefore(LocalDateTime.now())
                        && e.getDateOfEvent().isBefore(LocalDateTime.now().plusDays(upcomingThresholdDays)))
                .toList();
    }

    @Override
    public List<Racer> getAllRacersForNearestEvent() {
        return eventRepository.findAll()
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
        return eventRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getTrack().getId(), track.getId()))
                .toList();
    }
}
