package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.exceptions.AlreadyExistedException;
import com.fmi.web.lab.raceeventmanagement.repository.sequence.TrackSequence;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Component
public class TrackRepository {
    private Map<Integer, Track> trackMapTable = new HashMap<>();

    /**
     * Add track to your DB. If the track is already present throw Custom Exception
     * @param track
     */
    public void addTrack(Track track) {
        if (track.getId() != null) {
            throw new AlreadyExistedException(String.format("Track with id %s is already in DB", track.getId()));
        }

        track.setId(TrackSequence.getNextValue());
        trackMapTable.put(track.getId(), track);
    }

    /**
     * Modify track from your DB
     * @param track
     */
    public void updateTrack(Track track) {
        if (!trackMapTable.containsKey(track.getId())) {
            throw new NoSuchElementException(String.format("Track with id %s is not" +
                    " already in DB to be updated", track.getId()));
        }

        trackMapTable.replace(track.getId(), track);
    }

    /**
     * Delete track by name. If there is no element to be deleted then return false;
     * @param name
     * @return if there is element to delete -> true, if not -> false
     */
    public boolean deleteTrackByName(String name) {
        Optional<Integer> toDelete = trackMapTable.entrySet()
                .stream()
                .filter(pair -> Objects.equals(pair.getValue().getName(), name))
                .map(es -> es.getKey())
                .findAny();

        toDelete.ifPresent(id -> trackMapTable.remove(id));
        return toDelete.isPresent();
    }

    /**
     * Get track by passed id. If there is no element return Optional empty.
     * @param id
     * @return Optional of Track
     */
    public Optional<Track> getTrackById(Integer id) {
        return Optional.ofNullable(trackMapTable.get(id));
    }

    /**
     * Get all tracks
     * @return List of Tracks
     */
    public List<Track> getAllTracks() {
        return trackMapTable.values().stream().toList();
    }
}
