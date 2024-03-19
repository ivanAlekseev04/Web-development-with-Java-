package service;

import entity.Racer;
import entity.Team;
import repository.RacerRepository;
import repository.TeamRepository;
import validator.StringValidator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TeamService implements TeamServiceAPI {
    private static final int MAX_LENGTH = 60;

    @Override
    public void createTeam(String name) {
        validateTeamAttributes(name);

        TeamRepository.getInstance().addTeam(new Team(name));
    }

    @Override
    public void updateTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

        validateTeamAttributes(team.getName());

        TeamRepository.getInstance().updateTeam(team);
    }

    @Override
    public boolean deleteTeamByName(String name) {
        StringValidator.validate(name, "name");

        return TeamRepository.getInstance().deleteTeamByName(name);
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        StringValidator.validate(name, "name");

        return TeamRepository.getInstance().getTeamByName(name);
    }

    @Override
    public List<Team> getAllTeams() {
        return TeamRepository.getInstance().getAllTeams();
    }

    @Override
    public void addRacerToTeam(String teamName, Racer racer) {
        StringValidator.validate(teamName, "teamName");

        if (racer == null) {
            throw new IllegalArgumentException("Racer cannot be null");
        }

        if (TeamRepository.getInstance().getTeamByName(teamName).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no team named %s in DB", teamName));
        } else if (RacerRepository.getInstance().getRacerById(racer.getId()).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no racer with id %s in DB"
                    , (racer.getId() == null) ? "null" : racer.getId() ));
        }

        TeamRepository.getInstance().getTeamByName(teamName).get().addRacer(racer);
    }

    @Override
    public void deleteRacerFromTeamById(String teamName, Integer id) {
        StringValidator.validate(teamName, "teamName");

        if (id == null) {
            throw new IllegalArgumentException("Racer's Id cannot be null");
        }

        if (TeamRepository.getInstance().getTeamByName(teamName).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no team named %s in DB", teamName));
        } else if (RacerRepository.getInstance().getRacerById(id).isEmpty()) {
            throw new NoSuchElementException(String.format("There is no racer with id %s in DB", id));
        }

        TeamRepository.getInstance().getTeamByName(teamName).get().deleteRacer(id);
    }

    private void validateTeamAttributes(String name) {
        StringValidator.validate(name, "name");

        if (name.length() > MAX_LENGTH || TeamRepository.getInstance().getTeamByName(name).isPresent()) {
            throw new IllegalArgumentException("Team's name need to be maximum of 60 characters length" +
                    " and every name need to be unique");
        }
    }
}
