package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.exceptions.AlreadyExistedException;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Component
public class TeamService implements TeamServiceAPI {
    private static final int MAX_LENGTH = 60;
    @Autowired
    ILogger logger;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RacerRepository racerRepository;

    @Override
    public void createTeam(String name) {
        teamRepository.addTeam(new Team(name, Collections.emptyList()));
        logger.info(String.format("Team '%s' was created", name));
    }

    @Override
    public void updateTeam(Team team) {
        teamRepository.updateTeam(team);
    }

    @Override
    public boolean deleteTeamByName(String name) {
        return teamRepository.deleteTeamByName(name);
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        return teamRepository.getTeamByName(name);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    @Override
    public void addRacerToTeam(String teamName, Racer racer) {
        if (racer == null) {
            throw new IllegalArgumentException("Racer cannot be null");
        }

        if (teamRepository.getTeamByName(teamName).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no team named %s in DB", teamName));
        }
        /*
        else if (racerRepository.getRacerById(racer.getId()).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no racer with id %s in DB"
                    , (racer.getId() == null) ? "null" : racer.getId() ));
        } else if (teamRepository.getTeamByName(teamName).get()
                .getRacers()
                .stream()
                .anyMatch(r -> Objects.equals(r.getId(), racer.getId()))) {

            throw new AlreadyExistedException(String.format("Racer with id %s is already existed in team %s"
                    , racer.getId(), teamName));
        */

        teamRepository.getTeamByName(teamName).get().getRacers().add(racer);
    }

    @Override
    public boolean deleteRacerFromTeamById(String teamName, Integer id) {
        if (teamRepository.getTeamByName(teamName).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no team named %s in DB", teamName));
        }
        /*
        else if (racerRepository.getRacerById(id).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no racer with id %s in DB", id));
        }
        */

        return teamRepository.getTeamByName(teamName).get()
                .getRacers()
                .removeIf(r -> Objects.equals(r.getId(), id));
    }
}
