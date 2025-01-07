package com.fmi.raceeventmanagement.repository;

import com.fmi.raceeventmanagement.model.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByTrackId(Integer trackId);
}