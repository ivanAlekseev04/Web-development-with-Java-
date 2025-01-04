package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService implements TeamServiceAPI {

    private final TeamRepository teamRepository;
    private final RacerRepository racerRepository;

    @Override
    public Team createTeam(String name, Set<Racer> racers) {
        if(teamRepository.findById(name).isPresent()) {
            throw new DataIntegrityViolationException(String.format("Team with name %s is already existed", name));
        }

        log.info(String.format("Team '%s' was created", name));

        Team newTeam = teamRepository.save(new Team(name, racers));
        assignTeamToRacers(newTeam, racers);

        return newTeam;
    }

    @Override
    public Team updateTeam(Team team) {
        var toUpdate = teamRepository.findById(team.getName());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Team with name %s is not" +
                    " already in DB to be updated", team.getName()));
        }

        if (team.getRacers() != null) {
            assignTeamToRacers(null, toUpdate.get().getRacers());
            assignTeamToRacers(team, team.getRacers());

            toUpdate.get().setRacers(team.getRacers());
        }

        return teamRepository.save(toUpdate.get());
    }

    @Override
    public void deleteTeamByName(String name) {
        teamRepository.deleteById(name);
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        return teamRepository.findById(name);
    }

    @Override
    public Set<Team> getAllTeams() {
        return new HashSet<>(teamRepository.findAll());
    }

    @Override
    public Set<Racer> addRacerToTeam(String teamName, Racer racer) {
        if (racer == null) {
            throw new IllegalArgumentException("Racer cannot be null");
        }

        var toUpdate = teamRepository.findById(teamName);

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("There is no team named %s in DB", teamName));
        }

        assignTeamToRacers(toUpdate.get(), Set.of(racer));

        return teamRepository.findById(teamName).get().getRacers();
    }

    @Override
    public Set<Racer> deleteRacerFromTeamById(String teamName, Integer id) {
        var toUpdate = teamRepository.findById(teamName);

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("There is no team named %s in DB", teamName));
        }

        racerRepository.findById(id).ifPresent(racer -> assignTeamToRacers(null, Set.of(racer)));

        return teamRepository.findById(teamName).get().getRacers();
    }

    private void assignTeamToRacers(Team team, Set<Racer> racers) {
        racers.forEach(r -> {
            var actualRacer = racerRepository.findById(r.getId());

            if(actualRacer.isPresent()) {
                if(actualRacer.get().getTeam() != null) {
                    throw new DataIntegrityViolationException(String.format("Racer with id %d is already assigned to team %s"
                            , r.getId(), actualRacer.get().getTeam().getName()));
                }

                actualRacer.get().setTeam(team);
                racerRepository.save(actualRacer.get());
            } else {
                throw new EntityNotFoundException(String.format("Racer with id %d was not found in DB", r.getId()));
            }
        });
    }
}
