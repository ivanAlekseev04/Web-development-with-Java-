package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamService implements TeamServiceAPI {
    private static final int MAX_LENGTH = 60;
    @Autowired
    ILogger logger;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RacerRepository racerRepository;

    @Override
    public Team createTeam(String name, Set<Racer> racers) {
        if(teamRepository.findById(name).isPresent()) {
            throw new DataIntegrityViolationException(String.format("Team with name %s is already existed", name));
        }

        logger.info(String.format("Team '%s' was created", name));

        Team newTeam = teamRepository.save(new Team(name, racers));
        racerRepository.assignTeamToRacers(newTeam, racers);

        return newTeam;
    }

    @Override
    public Team updateTeam(Team team) {
        return teamRepository.update(team, racerRepository);
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

    public Set<Racer> addRacerToTeam(String teamName, Racer racer) {
        if (racer == null) {
            throw new IllegalArgumentException("Racer cannot be null");
        }

        var toUpdate = teamRepository.findById(teamName);

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("There is no team named %s in DB", teamName));
        }

        racerRepository.assignTeamToRacers(toUpdate.get(), Set.of(racer));

        return teamRepository.findById(teamName).get().getRacers();
    }

    public Set<Racer> deleteRacerFromTeamById(String teamName, Integer id) {
        var toUpdate = teamRepository.findById(teamName);

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("There is no team named %s in DB", teamName));
        }

        racerRepository.findById(id)
                .ifPresent(racer -> racerRepository.assignTeamToRacers(null, Set.of(racer)));

        return teamRepository.findById(teamName).get().getRacers();
    }
}
