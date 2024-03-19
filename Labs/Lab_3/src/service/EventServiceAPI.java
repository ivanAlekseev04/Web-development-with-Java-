package service;

import entity.Event;
import entity.Racer;
import entity.Track;

import java.time.LocalDate;
import java.util.List;

public interface EventServiceAPI {
    List<Event> getAllEvents();

    List<Racer> getAllRacersByTrackName(String trackName);

    void createEvent(String name, Track track, LocalDate dateOfEvent);

    void updateEvent(Event event);

    boolean deleteEventById(Integer id);

    List<Event> getAllEventsBefore(LocalDate date);

    List<Event> getAllEventsAfter(LocalDate date);

    List<Event> getUpcomingEvents();

    /**
     * Get all racers from the nearest event (timely manner)
     * @return List of all racers
     */
    List<Racer> getAllRacersForNearestEvent();

    List<Event> getEventsForTrack(Track track);
}

