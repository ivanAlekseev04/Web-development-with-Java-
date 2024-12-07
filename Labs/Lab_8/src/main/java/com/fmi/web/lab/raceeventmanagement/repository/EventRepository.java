package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Event;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    default Event update(Event event, TrackRepository trackRepository
            , TeamRepository teamRepository) {
        var toUpdate = this.findById(event.getId());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %s is not" +
                    " already in DB to be updated", event.getId()));
        }

        if (event.getName() != null) {
            if(event.getName().isBlank() || event.getName().isEmpty()) {
                throw new IllegalArgumentException("Event: name need to have minimum 1 non-white space character");
            }

            toUpdate.get().setName(event.getName());
        }
        if(event.getDateOfEvent() != null) {
            toUpdate.get().setDateOfEvent(event.getDateOfEvent());
        }
//      When @OneToOne relationship will be bidirectional
//        if(event.getTrack() != null) {
//            trackRepository.assignEventToTrack(toUpdate.get(), event.getTrack()); //TODO: check this
//
//            toUpdate.get().setTrack(event.getTrack());
//        }
        if(event.getTeams() != null) {
            teamRepository.assignEventToTeams(null, toUpdate.get().getTeams());
            teamRepository.assignEventToTeams(toUpdate.get(), event.getTeams());

            toUpdate.get().setTeams(event.getTeams());
        }

        return this.save(toUpdate.get());
    }
}
