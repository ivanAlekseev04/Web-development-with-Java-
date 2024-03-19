package service;

import entity.Event;
import entity.Racer;
import entity.Team;
import entity.Track;
import repository.EventRepository;
import repository.TeamRepository;
import repository.TrackRepository;
import validator.StringValidator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class EventService implements EventServiceAPI {

    @Override
    public List<Event> getAllEvents() {
        return EventRepository.getInstance().getAllEvents();
    }

    @Override
    public List<Racer> getAllRacersByTrackName(String trackName) {
        return EventRepository.getInstance().getAllEvents()
                .stream()
                .filter(e -> e.getTrack().getName().equals(trackName))
                .flatMap(e -> e.getTeams()
                        .stream()
                        .flatMap(t -> t.getRacers().stream()))
                .toList();
    }

    @Override
    public void createEvent(String name, Track track, LocalDate dateOfEvent) {
        validateEventAttributes(name, track, dateOfEvent);

        EventRepository.getInstance().addEvent(new Event(name, track, dateOfEvent));
    }

    @Override
    public void updateEvent(Event event) {
        validateEventAttributes(event.getName(), event.getTrack(), event.getDateOfEvent());

        EventRepository.getInstance().updateEvent(event);
    }

    public void addTeamToEvent(Integer eventId, Team team) {
        if (eventId == null || eventId < 0) {
            throw new IllegalArgumentException("Invalid eventId. Id can't be negative or null");
        } else if (team == null) {
            throw new IllegalArgumentException("Team can't be null");
        }

        if (EventRepository.getInstance().getEventById(eventId).isEmpty()) {
            throw new NoSuchElementException(String.format("Event with id %s is not in DB", eventId));
        } else if (TeamRepository.getInstance().getTeamByName(team.getName()).isEmpty()) {
            throw new NoSuchElementException(String.format("Team name %s is not in DB",
                    team.getName() == null ? "null" : team.getName()));
        }

        EventRepository.getInstance().getEventById(eventId).get().addTeam(team);
    }

    public void deleteTeamFromEventByName(Integer eventId, String name) {
        StringValidator.validate(name, "name");

        if (eventId == null || eventId < 0) {
            throw new IllegalArgumentException("Invalid eventId. Id can't be negative or null");
        }

        if (EventRepository.getInstance().getEventById(eventId).isEmpty()) {
            throw new NoSuchElementException(String.format("Event with id %s is not in DB", eventId));
        } else if (TeamRepository.getInstance().getTeamByName(name).isEmpty()) {
            throw new NoSuchElementException(String.format("Team name %s is not in DB", name));
        }

        EventRepository.getInstance().getEventById(eventId).get().deleteTeam(name);
    }

    @Override
    public boolean deleteEventById(Integer id) {
        return EventRepository.getInstance().deleteEventById(id);
    }

    @Override
    public List<Event> getAllEventsBefore(LocalDate date) {
        return EventRepository.getInstance().getAllEvents()
                .stream()
                .filter(e -> e.getDateOfEvent().isBefore(date))
                .toList();
    }

    @Override
    public List<Event> getAllEventsAfter(LocalDate date) {
        return EventRepository.getInstance().getAllEvents()
                .stream()
                .filter(e -> e.getDateOfEvent().isAfter(date))
                .toList();
    }

    @Override
    public List<Event> getUpcomingEvents() {
        // Assume that "upcoming event" is event on a current date or on a future date
        return EventRepository.getInstance().getAllEvents()
                .stream()
                .filter(e -> !e.getDateOfEvent().isBefore(LocalDate.now()))
                .toList();
    }

    @Override
    public List<Racer> getAllRacersForNearestEvent() {
        // "nearest Event" can be treated as Event from the past or future Event
        // ascending order of sorting is default for Comparator.comparing()
        return EventRepository.getInstance().getAllEvents()
                .stream()
                .sorted(Comparator.comparingLong(f -> ChronoUnit.DAYS.between(LocalDate.now(), f.getDateOfEvent())))
                .findFirst()
                .map(e -> e.getTeams()
                        .stream()
                        .flatMap(t -> t.getRacers().stream()))
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Event> getEventsForTrack(Track track) {
        return EventRepository.getInstance().getAllEvents()
                .stream()
                .filter(e -> e.getTrack().getId().equals(track.getId()))
                .toList();
    }

    private void validateEventAttributes(String name, Track track, LocalDate dateOfEvent) {
        StringValidator.validate(name, "name");

        if (dateOfEvent == null || dateOfEvent.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid dateOfEvent value. LocalDate need" +
                    " to be not null and after or equal to current date");
        }

        if (track == null || track.getId() == null) {
            throw new IllegalArgumentException("Either Track can't be null or its id can't be null");
        } else if (TrackRepository.getInstance().getTrackById(track.getId()).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no Track added in DB with id %s", track.getId()));
        }
    }
}
