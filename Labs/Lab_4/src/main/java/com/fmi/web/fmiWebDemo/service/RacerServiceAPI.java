package com.fmi.web.fmiWebDemo.service;

import com.fmi.web.fmiWebDemo.entity.Racer;

import java.util.List;

public interface RacerServiceAPI {
    List<Racer> getAllRacers();

    List<Racer> getAllRacersByFirstName(String firstName);

    void createRacer(String firstName, String lastName, Integer age);

    void updateRacer(Racer racer);

    boolean deleteRacerById(Integer id);
}

