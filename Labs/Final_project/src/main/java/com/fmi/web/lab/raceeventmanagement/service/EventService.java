package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Event;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.repository.EventRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements EventServiceAPI {

    @Value("${config.event.upcoming-threshold-days}")
    Integer upcomingThresholdDays;

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final TrackRepository trackRepository;

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

        log.info(String.format("Event '%s' was created", name));
        Event newEvent = eventRepository.save(toCreate);
        assignEventToTeams(newEvent, teams);

        return newEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        var toUpdate = eventRepository.findById(event.getId());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %s is not" +
                    " already in DB to be updated", event.getId()));
        }

        if (event.getName() != null) {
            if(event.getName().isBlank() || event.getName().isEmpty()) {
                throw new IllegalArgumentException("Event: name need to have minimum 1 non-white space character");
            }

            toUpdate.get().setName(event.getName());
        }
        if(event.getDateOfEvent() != null) {
            toUpdate.get().setDateOfEvent(event.getDateOfEvent());
        }
        if(event.getTeams() != null) {
            assignEventToTeams(null, toUpdate.get().getTeams());
            assignEventToTeams(toUpdate.get(), event.getTeams());

            toUpdate.get().setTeams(event.getTeams());
        }

        return eventRepository.save(toUpdate.get());
    }

    @Override
    public Set<Team> addTeamToEvent(Integer eventId, Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

        var toUpdate = eventRepository.findById(eventId);

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %s is not in DB", eventId));
        }

        assignEventToTeams(toUpdate.get(), Set.of(team));

        return eventRepository.findById(eventId).get().getTeams();
    }

    @Override
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

    private void assignEventToTeams(Event event, Set<Team> teams) {
        teams.forEach(t -> {
            var actualTeam = teamRepository.findById(t.getName());

            if(actualTeam.isPresent()) {
                actualTeam.get().getEvents().add(event);
                teamRepository.save(actualTeam.get());
            } else {
                throw new EntityNotFoundException(String.format("Team with id %s was not found in DB", t.getName()));
            }
        });
    }
}
