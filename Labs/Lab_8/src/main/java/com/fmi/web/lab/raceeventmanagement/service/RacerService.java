package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RacerService implements RacerServiceAPI {
    @Autowired
    ILogger logger;
    @Autowired
    RacerRepository racerRepository;
    @Autowired
    TeamRepository teamRepository;

    @Override
    public Racer createRacer(String firstName, String lastName, Integer age) {
        logger.info(String.format("Racer '%s %s' was created", firstName, lastName));
        return racerRepository.save(new Racer(firstName, lastName, age));
    }

    @Override
    public List<Racer> getAllRacers() {
        return racerRepository.findAll();
    }

    @Override
    public List<Racer> getAllRacersByFirstName(String firstName) {
        return getAllRacers()
                .stream()
                .filter(r -> firstName.equals(r.getFirstName()))
                .toList();
    }

    @Override
    public Racer updateRacer(Racer racer) {
        return racerRepository.update(racer, teamRepository);
    }

    @Override
    public void deleteRacerById(Integer id) {
        racerRepository.deleteById(id);
    }
}
