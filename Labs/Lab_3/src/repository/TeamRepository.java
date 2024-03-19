package repository;

import entity.Team;
import exceptions.AlreadyExistedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TeamRepository implements TeamRepositoryAPI {
    private static TeamRepository teamRepository;
    private Map<String, Team> teamTable;

    private TeamRepository() {
        teamTable = new HashMap<>();
    }

    public static TeamRepository getInstance() {
        if (teamRepository == null) {
            teamRepository = new TeamRepository();
        }

        return teamRepository;
    }

    @Override
    public void addTeam(Team team) {
        if (teamTable.containsKey(team.getName())) {
            throw new AlreadyExistedException(String.format("Team with name <%s> is already existed", team.getName()));
        }

        teamTable.put(team.getName(), team);
    }

    @Override
    public void updateTeam(Team team) {
        if (!teamTable.containsKey(team.getName())) {
            throw new NoSuchElementException(String.format("Team with name %s is not" +
                    " already in DB to be updated", team.getName()));
        }

        teamTable.replace(team.getName(), team);
    }

    @Override
    public boolean deleteTeamByName(String name) {
        return teamTable.remove(name) != null;
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        return teamTable.entrySet()
                .stream()
                .filter(t -> t.getKey().equalsIgnoreCase(name))
                .map(pair -> pair.getValue())
                .findFirst();
    }

    @Override
    public List<Team> getAllTeams() {
        return teamTable.values().stream().toList();
    }
}

