package repository;

import entity.Track;
import exceptions.AlreadyExistedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class TrackRepository implements TrackRepositoryAPI {
    private static TrackRepository trackRepository;
    private Map<Integer, Track> trackMapTable;
    private static int counter;

    private TrackRepository() {
        trackMapTable = new HashMap<>();
    }

    public static TrackRepository getInstance() {
        if (trackRepository == null) {
            trackRepository = new TrackRepository();
        }

        return trackRepository;
    }

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
