package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;

import java.util.List;

public interface RacerServiceAPI {
    List<Racer> getAllRacers();

    List<Racer> getAllRacersByFirstName(String firstName);

    Racer createRacer(String firstName, String lastName, Integer age);

    Racer updateRacer(Racer racer);

    boolean deleteRacerById(Integer id);
}

