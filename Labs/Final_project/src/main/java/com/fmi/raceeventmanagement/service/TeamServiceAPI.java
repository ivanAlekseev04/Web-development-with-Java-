package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.TeamDTO;
import com.fmi.raceeventmanagement.model.Racer;
import com.fmi.raceeventmanagement.model.Team;

import java.util.Optional;
import java.util.Set;

public interface TeamServiceAPI {

    Team createTeam(String name);

    void updateTeam(Team team);

    void deleteTeamByName(String name);

    Optional<TeamDTO> getTeamByName(String name);

    Set<TeamDTO> getAllTeams();

    void addRacerToTeam(String teamName, Racer racer);

    void deleteRacerFromTeamById(String teamName, Integer id);
}
