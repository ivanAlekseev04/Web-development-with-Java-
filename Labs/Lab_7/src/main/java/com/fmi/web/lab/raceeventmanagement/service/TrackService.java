package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.TrackRepository;
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
        return getAllTracks()
                .stream()
                .filter(t -> t.getName().equals(name))
                .toList();
    }

    @Override
    public void createTrack(String name, Integer length) {
        trackRepository.addTrack(new Track(name, length));
        logger.info(String.format("Track '%s' was created", name));
    }

    @Override
    public void updateTrack(Track track) {
        trackRepository.updateTrack(track);
    }

    @Override
    public boolean deleteTrackByName(String name) {
        return trackRepository.deleteTrackByName(name);
    }
}
