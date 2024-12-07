package com.fmi.web.lab.raceeventmanagement.entity;

import java.util.LinkedList;
import java.util.List;

public class Team {
    private String name;
    private List<Racer> racers;

    public Team(String name) {
        this.name = name;
        racers = new LinkedList<>(); // LinkedList because of add/delete operations,
        // that can be performed frequently
    }

    public String getName() {
        return name;
    }

    public List<Racer> getRacers() {
        return racers;
    }
}
