package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.EventDTO;
import com.fmi.raceeventmanagement.dto.RacerDTO;
import com.fmi.raceeventmanagement.exceptions.ValidationException;
import com.fmi.raceeventmanagement.mapper.EventMapper;
import com.fmi.raceeventmanagement.mapper.RacerMapper;
import com.fmi.raceeventmanagement.model.Event;
import com.fmi.raceeventmanagement.model.Team;
import com.fmi.raceeventmanagement.repository.EventRepository;
import com.fmi.raceeventmanagement.repository.TeamRepository;
import com.fmi.raceeventmanagement.repository.TrackRepository;
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
    private final EventMapper eventMapper;
    private final RacerMapper racerMapper;

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(e -> eventMapper.eventToDTO(e))
                .toList();
    }

    @Override
    public List<RacerDTO> getAllRacersByTrackName(String trackName) {
        return eventRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getTrack().getName(), trackName))
                .flatMap(e -> e.getTeams()
                        .stream()
                        .flatMap(t -> t.getRacers().stream()))
                .map(racer -> racerMapper.racerToDTO(racer))
                .toList();
    }

    @Override
    public Event createEvent(String name, Integer trackId, LocalDateTime dateOfEvent) {
        var actualTrack = trackRepository.findById(trackId);

        if(actualTrack.isEmpty()) {
            throw new ValidationException(String.format("Track with id %s is not already in DB", trackId));
        }

        Event toCreate = new Event(name, actualTrack.get(), dateOfEvent);

        if(Set.of(teamRepository.findAll()).contains(toCreate)) {
            throw new ValidationException(String.format("Event with name %s on track %s on a date %s " +
                    "is already existed", name, actualTrack.get().getName(), dateOfEvent.toString()));
        }

        log.info(String.format("Event '%s' was created", name));

        return eventRepository.save(toCreate);
    }

    @Override
    public void updateEvent(Event event) {
        var toUpdate = eventRepository.findById(event.getId());

        if (toUpdate.isEmpty()) {
            throw new ValidationException(String.format("Event with id %s is not" +
                    " already in DB to be updated", event.getId()));
        }

        if (event.getName() != null) {
            if(event.getName().isBlank() || event.getName().isEmpty()) {
                throw new ValidationException("Need to have minimum 1 non-white space character", "name");
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

        eventRepository.save(toUpdate.get());
    }

    @Override
    public void addTeamToEvent(Integer eventId, Team team) {
        if (team == null) {
            throw new ValidationException("Cannot be null", "Team");
        }

        var toUpdate = eventRepository.findById(eventId);

        if (toUpdate.isEmpty()) {
            throw new ValidationException(String.format("Event with id %s is not in DB", eventId));
        }

        assignEventToTeams(toUpdate.get(), Set.of(team));
    }

    @Override
    public void deleteTeamFromEventByName(Integer eventId, String name) {
        var toUpdate = eventRepository.findById(eventId);

        if (toUpdate.isEmpty()) {
            throw new ValidationException(String.format("Event with id %s is not in DB", eventId));
        }

        teamRepository.findById(name).ifPresent(t -> {
            t.getEvents().remove(toUpdate.get());
            teamRepository.save(t);
        });
    }

    @Override
    public void deleteEventById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDTO> getAllEventsBefore(LocalDateTime date) {
        return eventRepository.findAll()
                .stream()
                .filter(e -> e.getDateOfEvent().isBefore(date))
                .map(e -> eventMapper.eventToDTO(e))
                .toList();
    }

    @Override
    public List<EventDTO> getAllEventsAfter(LocalDateTime date) {
        return eventRepository.findAll()
                .stream()
                .filter(e -> e.getDateOfEvent().isAfter(date))
                .map(e -> eventMapper.eventToDTO(e))
                .toList();
    }

    @Override
    public List<EventDTO> getUpcomingEvents() {
        // Assume that "upcoming event" is event on a current date or on a future date
        return eventRepository.findAll()
                .stream()
                .filter(e -> !e.getDateOfEvent().isBefore(LocalDateTime.now())
                        && e.getDateOfEvent().isBefore(LocalDateTime.now().plusDays(upcomingThresholdDays)))
                .map(e -> eventMapper.eventToDTO(e))
                .toList();
    }

    @Override
    public List<RacerDTO> getAllRacersForNearestEvent() {
        return eventRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(f -> ChronoUnit.SECONDS.between(LocalDateTime.now(), f.getDateOfEvent())))
                .findFirst()
                .map(e -> e.getTeams()
                        .stream()
                        .flatMap(t -> t.getRacers().stream())
                        .map(r -> racerMapper.racerToDTO(r)))
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<EventDTO> getEventsForTrack(Integer trackId) {
        return eventRepository.findByTrackId(trackId).stream()
                .map(e -> eventMapper.eventToDTO(e))
                .toList();
    }

    private void assignEventToTeams(Event event, Set<Team> teams) {
        teams.forEach(t -> {
            var actualTeam = teamRepository.findById(t.getName());

            if(actualTeam.isPresent()) {
                actualTeam.get().getEvents().add(event);
                teamRepository.save(actualTeam.get());
            } else {
                throw new ValidationException(String.format("Team with id %s was not found in DB", t.getName()));
            }
        });
    }
}