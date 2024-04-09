package com.fmi.web.lab.raceeventmanagement.model;

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

    public Team(String name, List<Racer> racers) {
        this.name = name;
        this.racers = racers;
    }

    public String getName() {
        return name;
    }

    public List<Racer> getRacers() {
        return racers;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", racers=" + racers +
                '}';
    }
}
