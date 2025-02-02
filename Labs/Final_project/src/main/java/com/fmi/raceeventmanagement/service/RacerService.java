package com.fmi.raceeventmanagement.service;

import com.fmi.raceeventmanagement.dto.RacerDTO;
import com.fmi.raceeventmanagement.exceptions.ValidationException;
import com.fmi.raceeventmanagement.mapper.RacerMapper;
import com.fmi.raceeventmanagement.model.Racer;
import com.fmi.raceeventmanagement.model.Team;
import com.fmi.raceeventmanagement.repository.RacerRepository;
import com.fmi.raceeventmanagement.repository.TeamRepository;
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
    private final RacerMapper racerMapper;

    @Override
    public Racer createRacer(String firstName, String lastName, Integer age) {
        log.info(String.format("Racer '%s %s' was created", firstName, lastName));

        return racerRepository.save(new Racer(firstName, lastName, age));
    }

    @Override
    public List<RacerDTO> getAllRacers() {
        return racerRepository.findAll().stream()
                .map(r -> racerMapper.racerToDTO(r))
                .toList();
    }

    @Override
    public List<RacerDTO> getAllRacersByFirstName(String firstName) {
        return getAllRacers()
                .stream()
                .filter(r -> firstName.equals(r.getFirstName()))
                .toList();
    }

    @Override
    public void updateRacer(Racer racer) {
        var toUpdate = racerRepository.findById(racer.getId());

        if (toUpdate.isEmpty()) {
            throw new ValidationException(String.format("Racer with id %s is not" +
                    " already in DB to be updated", racer.getId()));
        }

        if (racer.getFirstName() != null) {
            if(racer.getFirstName().isEmpty() || racer.getFirstName().isBlank()) {
                throw new ValidationException("Need to have minimum 1 non-white space character", "firstName");
            }

            toUpdate.get().setFirstName(racer.getFirstName());
        }
        if(racer.getLastName() != null) {
            if(racer.getLastName().isEmpty() || racer.getLastName().isBlank()) {
                throw new ValidationException("Need to have minimum 1 non-white space character", "lastName");
            }

            toUpdate.get().setLastName(racer.getLastName());
        }
        if(racer.getAge() != null) {
            if(racer.getAge() < 0) {
                throw new ValidationException("Can't be negative", "age");
            }

            toUpdate.get().setAge(racer.getAge());
        }
        if(racer.getTeam() != null) {
            assignRacerToTeam(toUpdate.get(), racer.getTeam());
        }

        racerRepository.save(toUpdate.get());
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
