package entity;

import exceptions.AlreadyExistedException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Event {
    private Integer id;
    private String name;
    private Track track;
    private List<Team> teams;
    private LocalDate dateOfEvent;

    public Event(String name, Track track, LocalDate dateOfEvent) {
        this.name = name;
        this.track = track;
        this.dateOfEvent = dateOfEvent;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfEvent() {
        return dateOfEvent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Track getTrack() {
        return track;
    }

    public boolean isExisted(String teamName) {
        return teams.stream().anyMatch(t -> Objects.equals(t.getName(), teamName));
    }

    public void addTeam(Team team) {
        if (isExisted(team.getName())) {
            throw new AlreadyExistedException(String.format("Team with specified name %s already exists in event %s",
                    team.getName(), id));
        }

        teams.add(team);
    }

    public void deleteTeam(String teamName) {
        if (!isExisted(teamName)) {
            throw new NoSuchElementException(String.format("Team with specified name %s isn't exist in event %s",
                    teamName, id));
        }

        teams.removeIf(t -> Objects.equals(t.getName(), teamName));
    }
}
