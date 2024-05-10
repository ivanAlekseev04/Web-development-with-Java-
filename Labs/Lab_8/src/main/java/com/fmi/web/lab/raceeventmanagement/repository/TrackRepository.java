package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Track;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TrackRepository extends JpaRepository<Track, Integer> {
    void deleteByName(String name);

    default Track update(Track track) {
        var toUpdate = this.findById(track.getId());

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

        return this.save(toUpdate.get());
    }

//    When @OneToOne relationship will be bidirectional
//    default void assignEventToTrack(Event event, Track track) {
//        var actualTrack = this.findById(track.getId());
//
//        if(actualTrack.isEmpty()) {
//            throw new EntityNotFoundException(String.format("Track with id %d is not" +
//                    " already in DB to be updated", actualTrack.get().getId()));
//        }
//
//        actualTrack.get().setEvent(event);
//    }
}
