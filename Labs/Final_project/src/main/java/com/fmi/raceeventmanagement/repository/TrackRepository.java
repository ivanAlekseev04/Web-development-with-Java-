package com.fmi.raceeventmanagement.repository;

import com.fmi.raceeventmanagement.model.Track;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface TrackRepository extends JpaRepository<Track, Integer> {
    void deleteByName(String name);
    Optional<Track> findByName(String name);
}
