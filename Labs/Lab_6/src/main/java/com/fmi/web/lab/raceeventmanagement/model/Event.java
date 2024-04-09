package com.fmi.web.lab.raceeventmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private Integer id;
    private String name;
    private Track track;
    private List<Team> teams;
    private LocalDateTime dateOfEvent;

    public Event(String name, Track track, LocalDateTime dateOfEvent) {
        this.name = name;
        this.track = track;
        this.dateOfEvent = dateOfEvent;
    }

    public Event(String name, Track track, List<Team> teams, LocalDateTime dateOfEvent) {
        this.name = name;
        this.track = track;
        this.teams = teams;
        this.dateOfEvent = dateOfEvent;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateOfEvent() {
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

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", track=" + track +
                ", teams=" + teams +
                ", dateOfEvent=" + dateOfEvent +
                '}';
    }
}
