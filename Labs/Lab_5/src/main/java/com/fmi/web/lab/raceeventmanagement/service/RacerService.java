package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.entity.Racer;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;
import com.fmi.web.lab.raceeventmanagement.util.StringValidator;
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
        StringValidator.validate(firstName, "firstName");

        return getAllRacers()
                .stream()
                .filter(r -> firstName.equals(r.getFirstName()))
                .toList();
    }

    @Override
    public void createRacer(String firstName, String lastName, Integer age) {
        validateRacerAttributes(firstName, lastName, age);

        racerRepository.addRacer(new Racer(firstName, lastName, age));
        logger.info(String.format("Racer '%s %s' was created", firstName, lastName));
    }

    @Override
    public void updateRacer(Racer racer) {
        if (racer == null) {
            throw new IllegalArgumentException("Racer cannot be null");
        }

        validateRacerAttributes(racer.getFirstName(), racer.getLastName(), racer.getAge());
        // Assume that we can update everything except the "id"
        racerRepository.updateRacer(racer);

    }

    @Override
    public boolean deleteRacerById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return racerRepository.deleteRacerById(id);
    }

    private void validateRacerAttributes(String firstName, String lastName, Integer age) {
        StringValidator.validate(firstName, "firstName");
        StringValidator.validate(lastName, "lastName");

        if (age == null || age <= 0) {
            throw new IllegalArgumentException("Racer's age cannot be null/less or equal to 0");
        }
    }
}
