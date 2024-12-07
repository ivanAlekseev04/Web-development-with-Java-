package com.fmi.web.lab.raceeventmanagement.entity;

import java.time.LocalDate;
import java.util.List;

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
}
