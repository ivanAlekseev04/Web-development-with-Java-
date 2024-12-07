package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Track;

import java.util.List;

public interface TrackServiceAPI {
    List<Track> getAllTracks();

    List<Track> getAllTracksByName(String name);

    Track createTrack(String name, Integer length);

    Track updateTrack(Track track);

    void deleteTrackByName(String name);
}
