package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.TrackDTO;
import com.fmi.raceeventmanagement.model.Track;

import java.util.List;

public interface TrackServiceAPI {
    List<Track> getAllTracks();

    List<TrackDTO> getAllTracksByName(String name);

    Track createTrack(String name, Integer length);

    void updateTrack(Track track);

    void deleteTrackByName(String name);
}