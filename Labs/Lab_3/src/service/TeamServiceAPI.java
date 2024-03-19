package service;

import entity.Racer;
import entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamServiceAPI {

    void createTeam(String name);

    void updateTeam(Team team);

    boolean deleteTeamByName(String name);

    Optional<Team> getTeamByName(String name);

    List<Team> getAllTeams();

    /**
     * Adds already existed racer in RacerRepository to the specified team
     * @param teamName
     * @param racer
     */
    void addRacerToTeam(String teamName, Racer racer);

    /**
     * Deletes already existed racer in RacerRepository by its id from the specified team
     * @param teamName
     * @param id
     */
    void deleteRacerFromTeamById(String teamName, Integer id);
}
