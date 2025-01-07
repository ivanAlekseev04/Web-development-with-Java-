package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.RacerDTO;
import com.fmi.raceeventmanagement.model.Racer;

import java.util.List;

public interface RacerServiceAPI {
    List<RacerDTO> getAllRacers();

    List<RacerDTO> getAllRacersByFirstName(String firstName);

    Racer createRacer(String firstName, String lastName, Integer age);

    void updateRacer(Racer racer);

    void deleteRacerById(Integer id);
}