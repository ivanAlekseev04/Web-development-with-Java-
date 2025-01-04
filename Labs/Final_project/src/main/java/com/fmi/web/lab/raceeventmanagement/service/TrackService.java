package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.repository.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackService implements TrackServiceAPI {

    private final TrackRepository trackRepository;

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
        log.info(String.format("Track '%s' was created", name));

        return trackRepository.save(new Track(name, length));
    }

    @Override
    public Track updateTrack(Track track) {
        var toUpdate = trackRepository.findById(track.getId());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Track with id %s is not" +
                    " already in DB to be updated", track.getId()));
        }

        if (track.getName() != null) {
            toUpdate.get().setName(track.getName());
        }
        if(track.getLength() != null) {
            toUpdate.get().setLength(track.getLength());
        }

        return trackRepository.save(toUpdate.get());
    }

    @Override
    public void deleteTrackByName(String name) {
        trackRepository.deleteByName(name);
    }
}
