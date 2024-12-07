package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.exceptions.AlreadyExistedException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class TeamRepository {
    private Map<String, Team> teamTable = new HashMap<>();

    /**
     * Add team to your DB. If the Team is already present throw Custom Exception
     * @param team
     */
    public void addTeam(Team team) {
        if (teamTable.containsKey(team.getName())) {
            throw new AlreadyExistedException(String.format("Team with name <%s> is already existed", team.getName()));
        }

        teamTable.put(team.getName(), team);
    }

    /**
     * Modify team from your DB
     * @param team
     */
    public void updateTeam(Team team) {
        if (!teamTable.containsKey(team.getName())) {
            throw new NoSuchElementException(String.format("Team with name %s is not" +
                    " already in DB to be updated", team.getName()));
        }

        teamTable.replace(team.getName(), team);
    }

    /**
     * Delete team by name. If there is no element to be deleted then return false;
     * @param name
     * @return if there is element to delete -> true, if not -> false
     */
    public boolean deleteTeamByName(String name) {
        return teamTable.remove(name) != null;
    }

    /**
     * Get team by passed name. If there is no element return Optional empty. The search is case-insensitive
     * @param name
     * @return Optional of Team
     */
    public Optional<Team> getTeamByName(String name) {
        return teamTable.entrySet()
                .stream()
                .filter(t -> t.getKey().equalsIgnoreCase(name))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    /**
     * Get all teams
     * @return List of all teams
     */
    public List<Team> getAllTeams() {
        return teamTable.values().stream().toList();
    }
}

