package com.fmi.web.fmiWebDemo.repository;

import com.fmi.web.fmiWebDemo.entity.Track;

import java.util.List;
import java.util.Optional;

public interface TrackRepositoryAPI {

    /**
     * Add track to your DB. If the track is already present throw Custom Exception
     * @param track
     */
    void addTrack(Track track);

    /**
     * Modify track from your DB
     * @param track
     */
    void updateTrack(Track track);

    /**
     * Delete track by name. If there is no element to be deleted then return false;
     * @param name
     * @return if there is element to delete -> true, if not -> false
     */
    boolean deleteTrackByName(String name);

    /**
     * Get track by passed id. If there is no element return Optional empty.
     * @param id
     * @return Optional of Track
     */
    Optional<Track> getTrackById(Integer id);

    /**
     * Get all tracks
     * @return List of Tracks
     */
    List<Track> getAllTracks();
}
