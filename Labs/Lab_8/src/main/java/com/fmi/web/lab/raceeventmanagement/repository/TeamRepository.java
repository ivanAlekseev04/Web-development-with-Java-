package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Event;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, String> {
    default Team update(Team team, RacerRepository racerRepository) {
        var toUpdate = this.findById(team.getName());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Team with name %s is not" +
                    " already in DB to be updated", team.getName()));
        }

        if (team.getRacers() != null) {
            racerRepository.assignTeamToRacers(null, toUpdate.get().getRacers());
            racerRepository.assignTeamToRacers(team, team.getRacers());

            toUpdate.get().setRacers(team.getRacers());
        }

        return this.save(toUpdate.get());
    }

    default void assignRacerToTeam(Racer racer, Team team) {
        var actualTeam = this.findById(team.getName());
        // TODO: check this when team isn't existed

        if(actualTeam.isEmpty()) {
            throw new EntityNotFoundException(String.format("Team with name %s is not" +
                    " already in DB to be updated", team.getName()));
        }

        racer.setTeam(actualTeam.get());
    }

    default void assignEventToTeams(Event event, Set<Team> teams) {
        teams.forEach(t -> {
            var actualTeam = this.findById(t.getName());

            if(actualTeam.isPresent()) {
                actualTeam.get().getEvents().add(event);
                this.save(actualTeam.get());
            } else {
                throw new EntityNotFoundException(String.format("Team with id %s was not found in DB", t.getName()));
            }
        });
    }
}

