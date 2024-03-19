package service;

import entity.Track;

import java.util.List;

public interface TrackServiceAPI {
    List<Track> getAllTracks();

    List<Track> getAllTracksByName(String name);

    void createTrack(String name, Integer length);

    void updateTrack(Track track);

    boolean deleteTrackByName(String name);
}
