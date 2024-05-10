package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
@Transactional
public interface RacerRepository extends JpaRepository<Racer, Integer> {
    default Racer update(Racer racer, TeamRepository teamRepository) {
        var toUpdate = this.findById(racer.getId());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Racer with id %s is not" +
                    " already in DB to be updated", racer.getId()));
        }

        if (racer.getFirstName() != null) {
            if(racer.getFirstName().isEmpty() || racer.getFirstName().isBlank()) {
                throw new IllegalArgumentException("Racer: firstName need to have minimum 1 non-white space character");
            }

            toUpdate.get().setFirstName(racer.getFirstName());
        }
        if(racer.getLastName() != null) {
            if(racer.getLastName().isEmpty() || racer.getLastName().isBlank()) {
                throw new IllegalArgumentException("Racer: lastName need to have minimum 1 non-white space character");
            }

            toUpdate.get().setLastName(racer.getLastName());
        }
        if(racer.getAge() != null) {
            if(racer.getAge() < 0) {
                throw new IllegalArgumentException("Racer: age can't be negative");
            }

            toUpdate.get().setAge(racer.getAge());
        }
        if(racer.getTeam() != null) {
            teamRepository.assignRacerToTeam(toUpdate.get(), racer.getTeam());
        }

        return this.save(toUpdate.get());
    }
    default void assignTeamToRacers(Team team, Set<Racer> racers) {
        racers.forEach(r -> {
            var actualRacer = this.findById(r.getId());

            if(actualRacer.isPresent()) {
                if(actualRacer.get().getTeam() != null) {
                    throw new DataIntegrityViolationException(String.format("Racer with id %d is already assigned to team %s"
                            , r.getId(), actualRacer.get().getTeam().getName()));
                }

                actualRacer.get().setTeam(team);
                this.save(actualRacer.get());
            } else {
                throw new EntityNotFoundException(String.format("Racer with id %d was not found in DB", r.getId()));
            }
        });
    }
}
