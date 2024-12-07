package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;

import java.util.List;

public interface RacerServiceAPI {
    List<Racer> getAllRacers();

    List<Racer> getAllRacersByFirstName(String firstName);

    void createRacer(String firstName, String lastName, Integer age);

    void updateRacer(Racer racer);

    boolean deleteRacerById(Integer id);
}

