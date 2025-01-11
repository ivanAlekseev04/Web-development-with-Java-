package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.TeamDTO;
import com.fmi.raceeventmanagement.exceptions.ValidationException;
import com.fmi.raceeventmanagement.mapper.RacerMapper;
import com.fmi.raceeventmanagement.mapper.TeamMapper;
import com.fmi.raceeventmanagement.model.Racer;
import com.fmi.raceeventmanagement.model.Team;
import com.fmi.raceeventmanagement.repository.RacerRepository;
import com.fmi.raceeventmanagement.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService implements TeamServiceAPI {

    private final TeamRepository teamRepository;
    private final RacerRepository racerRepository;
    private final TeamMapper teamMapper;
    private final RacerMapper racerMapper;

    @Override
    public Team createTeam(String name) {
        if (teamRepository.findById(name).isPresent()) {
            throw new ValidationException(String.format("Team with name %s is already existed", name));
        }

        log.info(String.format("Team '%s' was created", name));

        return teamRepository.save(new Team(name));
    }

    @Override
    public void updateTeam(Team team) {
        var toUpdate = teamRepository.findById(team.getName());

        if (toUpdate.isEmpty()) {
            throw new ValidationException(String.format("Team with name %s is not" +
                    " already in DB to be updated", team.getName()));
        }

        if (team.getRacers() != null) {
            assignTeamToRacers(null, toUpdate.get().getRacers());
            assignTeamToRacers(team, team.getRacers());

            toUpdate.get().setRacers(team.getRacers());
        }

        teamRepository.save(toUpdate.get());
    }

    @Override
    public void deleteTeamByName(String name) {
        teamRepository.deleteById(name);
    }

    @Override
    public Optional<TeamDTO> getTeamByName(String name) {
        return teamRepository.findById(name)
                .map(t -> teamMapper.teamToDTO(t));
    }

    @Override
    public Set<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(t -> teamMapper.teamToDTO(t))
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public void addRacerToTeam(String teamName, Integer id) {
        var toUpdate = teamRepository.findById(teamName);

        if (toUpdate.isEmpty()) {
            throw new ValidationException(String.format("There is no team named %s in DB", teamName));
        }

        assignTeamToRacers(toUpdate.get(), Set.of(racerRepository.findById(id).orElseThrow(
                () -> new ValidationException(String.format("There is no racer with id %d in DB", id)))));
    }

    @Override
    public void deleteRacerFromTeamById(String teamName, Integer id) {
        var toUpdate = teamRepository.findById(teamName);

        if (toUpdate.isEmpty()) {
            throw new ValidationException(String.format("There is no team named %s in DB", teamName));
        }

        assignTeamToRacers(null, Set.of(racerRepository.findById(id).orElseThrow(
                () -> new ValidationException(String.format("There is no racer with id %d in DB", id)))));
    }

    private void assignTeamToRacers(Team team, Set<Racer> racers) {
        racers.forEach(r -> {
            var actualRacer = racerRepository.findById(r.getId());

            if (actualRacer.isPresent()) {
                if (actualRacer.get().getTeam() != null && team != null) {
                    throw new ValidationException(String.format("Racer with id %d is already assigned to team %s"
                            , r.getId(), actualRacer.get().getTeam().getName()));
                }

                actualRacer.get().setTeam(team);
                racerRepository.save(actualRacer.get());
            } else {
                throw new ValidationException(String.format("Racer with id %d was not found in DB", r.getId()));
            }
        });
    }
}