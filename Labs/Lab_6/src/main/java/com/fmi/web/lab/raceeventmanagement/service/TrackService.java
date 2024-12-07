package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.TrackRepository;
import com.fmi.web.lab.raceeventmanagement.util.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackService implements TrackServiceAPI {
    @Autowired
    ILogger logger;
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
        logger.info(String.format("Track '%s' was created", name));
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
