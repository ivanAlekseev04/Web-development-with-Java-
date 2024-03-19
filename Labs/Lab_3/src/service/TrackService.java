package service;

import entity.Track;
import repository.TrackRepository;
import validator.StringValidator;

import java.util.List;

public class TrackService implements TrackServiceAPI {
    @Override
    public List<Track> getAllTracks() {
        return TrackRepository.getInstance().getAllTracks();
    }

    @Override
    public List<Track> getAllTracksByName(String name) {
        StringValidator.validate(name, "name");

        return getAllTracks()
                .stream()
                .filter(t -> t.getName().equals(name))
                .toList();
    }

    @Override
    public void createTrack(String name, Integer length) {
        validateTrackAttributes(name, length);

        TrackRepository.getInstance().addTrack(new Track(name, length));
    }

    @Override
    public void updateTrack(Track track) {
        if (track == null) {
            throw new IllegalArgumentException("Track must not be null");
        }

        validateTrackAttributes(track.getName(), track.getLength());

        TrackRepository.getInstance().updateTrack(track);
    }

    @Override
    public boolean deleteTrackByName(String name) {
        StringValidator.validate(name, "name");

        return TrackRepository.getInstance().deleteTrackByName(name);
    }

    private void validateTrackAttributes(String name, Integer length) {
        StringValidator.validate(name, "name");

        if (length == null || length < 0) {
            throw new IllegalArgumentException("Length must be greater than 0 and can't be null");
        }
    }
}
