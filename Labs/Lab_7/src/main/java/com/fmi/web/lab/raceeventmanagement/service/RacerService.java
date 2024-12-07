package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RacerService implements RacerServiceAPI {
    @Autowired
    ILogger logger;
    @Autowired
    RacerRepository racerRepository;

    @Override
    public List<Racer> getAllRacers() {
        return racerRepository.getAllRacers();
    }

    @Override
    public List<Racer> getAllRacersByFirstName(String firstName) {
        return getAllRacers()
                .stream()
                .filter(r -> firstName.equals(r.getFirstName()))
                .toList();
    }

    @Override
    public Racer createRacer(String firstName, String lastName, Integer age) {
        logger.info(String.format("Racer '%s %s' was created", firstName, lastName));
        return racerRepository.addRacer(new Racer(firstName, lastName, age));
    }

    @Override
    public Racer updateRacer(Racer racer) {
        return racerRepository.updateRacer(racer);
    }

    @Override
    public boolean deleteRacerById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return racerRepository.deleteRacerById(id);
    }
}
