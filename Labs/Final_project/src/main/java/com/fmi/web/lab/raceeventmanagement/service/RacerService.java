package com.fmi.web.lab.raceeventmanagement.service;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.repository.RacerRepository;
import com.fmi.web.lab.raceeventmanagement.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RacerService implements RacerServiceAPI {

    private final RacerRepository racerRepository;
    private final TeamRepository teamRepository;

    @Override
    public Racer createRacer(String firstName, String lastName, Integer age) {
        log.info(String.format("Racer '%s %s' was created", firstName, lastName));

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
        var toUpdate = racerRepository.findById(racer.getId());

        if (toUpdate.isEmpty()) {
            throw new EntityNotFoundException(String.format("Racer with id %s is not" +
                    " already in DB to be updated", racer.getId()));
        }

        if (racer.getFirstName() != null) {
            if(racer.getFirstName().isEmpty() || racer.getFirstName().isBlank()) {
                throw new IllegalArgumentException("Racer: firstName need to have minimum 1 non-white space character");
            }

            toUpdate.get().setFirstName(racer.getFirstName());
        }
        if(racer.getLastName() != null) {
            if(racer.getLastName().isEmpty() || racer.getLastName().isBlank()) {
                throw new IllegalArgumentException("Racer: lastName need to have minimum 1 non-white space character");
            }

            toUpdate.get().setLastName(racer.getLastName());
        }
        if(racer.getAge() != null) {
            if(racer.getAge() < 0) {
                throw new IllegalArgumentException("Racer: age can't be negative");
            }

            toUpdate.get().setAge(racer.getAge());
        }
        if(racer.getTeam() != null) {
            assignRacerToTeam(toUpdate.get(), racer.getTeam());
        }

        return racerRepository.save(toUpdate.get());
    }

    @Override
    public void deleteRacerById(Integer id) {
        racerRepository.deleteById(id);
    }

    private void assignRacerToTeam(Racer racer, Team team) {
        var actualTeam = teamRepository.findById(team.getName());

        if(actualTeam.isEmpty()) {
            throw new EntityNotFoundException(String.format("Team with name %s is not" +
                    " already in DB to be updated", team.getName()));
        }

        racer.setTeam(actualTeam.get());
    }
}
