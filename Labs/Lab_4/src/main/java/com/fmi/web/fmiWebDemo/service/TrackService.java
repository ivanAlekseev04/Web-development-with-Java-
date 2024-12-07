package com.fmi.web.fmiWebDemo.service;

import com.fmi.web.fmiWebDemo.entity.Track;
import com.fmi.web.fmiWebDemo.repository.TrackRepository;
import com.fmi.web.fmiWebDemo.util.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackService implements TrackServiceAPI {
    @Autowired
    TrackRepository trackRepository;

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.getAllTracks();
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

        trackRepository.addTrack(new Track(name, length));
    }

    @Override
    public void updateTrack(Track track) {
        if (track == null) {
            throw new IllegalArgumentException("Track must not be null");
        }

        validateTrackAttributes(track.getName(), track.getLength());

        trackRepository.updateTrack(track);
    }

    @Override
    public boolean deleteTrackByName(String name) {
        StringValidator.validate(name, "name");

        return trackRepository.deleteTrackByName(name);
    }

    private void validateTrackAttributes(String name, Integer length) {
        StringValidator.validate(name, "name");

        if (length == null || length < 0) {
            throw new IllegalArgumentException("Length must be greater than 0 and can't be null");
        }
    }
}
