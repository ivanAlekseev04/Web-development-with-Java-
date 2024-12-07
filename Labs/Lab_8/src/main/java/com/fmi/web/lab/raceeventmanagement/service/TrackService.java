package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrackService implements TrackServiceAPI {
    @Autowired
    ILogger logger;
    @Autowired
    TrackRepository trackRepository;

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public List<Track> getAllTracksByName(String name) {
        return getAllTracks()
                .stream()
                .filter(t -> t.getName().equals(name))
                .toList();
    }

    @Override
    public Track createTrack(String name, Integer length) {
        logger.info(String.format("Track '%s' was created", name));
        return trackRepository.save(new Track(name, length));
    }

    @Override
    public Track updateTrack(Track track) {
        return trackRepository.update(track);
    }

    @Override
    public void deleteTrackByName(String name) {
        trackRepository.deleteByName(name);
    }
}
