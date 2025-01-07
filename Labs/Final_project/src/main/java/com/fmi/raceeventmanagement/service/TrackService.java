package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.TrackDTO;
import com.fmi.raceeventmanagement.mapper.TrackMapper;
import com.fmi.raceeventmanagement.model.Track;
import com.fmi.raceeventmanagement.repository.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackService implements TrackServiceAPI {

    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public List<TrackDTO> getAllTracksByName(String name) {
        return getAllTracks()
                .stream()
                .filter(t -> t.getName().equals(name))
                .map(t -> trackMapper.trackToDTO(t))
                .toList();
    }

    @Override
    public Track createTrack(String name, Integer length) {
        if (trackRepository.findByName(name).isPresent()) {
            throw new DataIntegrityViolationException(String.format("Track with name %s is already existed !", name));
        }

        log.info(String.format("Track '%s' was created", name));

        return trackRepository.save(new Track(name, length));
    }

    @Override
    public void updateTrack(Track track) {
        var toUpdate = trackRepository.findById(track.getId());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Track with id %s is not" +
                    " already in DB to be updated", track.getId()));
        }

        if (track.getName() != null) {
            if(track.getName().isBlank() || track.getName().isEmpty()) {
                throw new IllegalArgumentException("Track: name need to have minimum 1 non-white space character");
            }

            toUpdate.get().setName(track.getName());
        }
        if(track.getLength() != null) {
            if(track.getLength() <= 0) {
                throw new IllegalArgumentException("Track: length need to be bigger than zero");
            }

            toUpdate.get().setLength(track.getLength());
        }
    }

    @Override
    public void deleteTrackByName(String name) {
        trackRepository.deleteByName(name);
    }
}