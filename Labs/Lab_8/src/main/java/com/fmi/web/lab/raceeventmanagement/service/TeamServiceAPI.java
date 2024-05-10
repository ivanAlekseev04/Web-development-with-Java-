package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeamServiceAPI {

    Team createTeam(String name, Set<Racer> racers);

    Team updateTeam(Team team);

    void deleteTeamByName(String name);

    Optional<Team> getTeamByName(String name);

    Set<Team> getAllTeams();
}
