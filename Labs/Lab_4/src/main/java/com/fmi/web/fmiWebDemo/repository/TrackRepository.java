package com.fmi.web.fmiWebDemo.repository;

import com.fmi.web.fmiWebDemo.entity.Track;
import com.fmi.web.fmiWebDemo.exceptions.AlreadyExistedException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Component
public class TrackRepository implements TrackRepositoryAPI {
    private Map<Integer, Track> trackMapTable = new HashMap<>();
    private static int counter;

    @Override
    public void addTrack(Track track) {
        if (track.getId() != null) {
            throw new AlreadyExistedException(String.format("Track with id %s is already in DB", track.getId()));
        }

        track.setId(counter++);
        trackMapTable.put(track.getId(), track);
    }

    @Override
    public void updateTrack(Track track) {
        if (!trackMapTable.containsKey(track.getId())) {
            throw new NoSuchElementException(String.format("Track with id %s is not" +
                    " already in DB to be updated", track.getId()));
        }

        trackMapTable.replace(track.getId(), track);
    }

    @Override
    public boolean deleteTrackByName(String name) {
        Optional<Integer> toDelete = trackMapTable.entrySet()
                .stream()
                .filter(pair -> Objects.equals(pair.getValue().getName(), name))
                .map(es -> es.getKey())
                .findAny();

        toDelete.ifPresent(id -> trackMapTable.remove(id));
        return toDelete.isPresent();
    }

    @Override
    public Optional<Track> getTrackById(Integer id) {
        return Optional.ofNullable(trackMapTable.get(id));
    }

    @Override
    public List<Track> getAllTracks() {
        return trackMapTable.values().stream().toList();
    }
}
