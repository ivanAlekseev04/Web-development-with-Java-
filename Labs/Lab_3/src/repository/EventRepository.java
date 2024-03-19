package repository;

import entity.Event;
import exceptions.AlreadyExistedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class EventRepository implements EventRepositoryAPI {
    private static EventRepository eventRepository;
    private Map<Integer, Event> eventTable;
    private static int counter;

    private EventRepository() {
        eventTable = new HashMap<>();
    }

    public static EventRepository getInstance() {
        if (eventRepository == null) {
            eventRepository = new EventRepository();
        }

        return eventRepository;
    }

    @Override
    public void addEvent(Event event) {
        if (event.getId() != null) {
            throw new AlreadyExistedException(String.format("Event with id %s is already in DB", event.getId()));
        }

        event.setId(counter++);
        eventTable.put(event.getId(), event);
    }

    @Override
    public void updateEvent(Event event) {
        if (!eventTable.containsKey(event.getId())) {
            throw new NoSuchElementException(String.format("Event with id %s is not already" +
                    " in DB to be updated", event.getId()));
        }

        eventTable.replace(event.getId(), event);
    }

    @Override
    public boolean deleteEventById(Integer id) {
        return eventTable.remove(id) != null;
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return Optional.ofNullable(eventTable.get(id));
    }

    @Override
    public List<Event> getAllEvents() {
        return eventTable.values().stream().toList();
    }
}
