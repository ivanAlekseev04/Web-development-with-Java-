package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.repository.sequence.RaceSequence;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class RacerRepository implements RacerRepositoryAPI {
    private Map<Integer, Racer> racerTable = new HashMap<>();

    @Override
    public void addRacer(Racer racer) {
        // As soon Racer instances are created inside another class with automatic "id" assignment,
        // then it can't be 2 identical racers at all
        racer.setId(RaceSequence.getNextValue());
        racerTable.put(racer.getId(), racer);
    }

    @Override
    public void updateRacer(Racer racer) {
        if (!racerTable.containsKey(racer.getId())) {
            throw new NoSuchElementException(String.format("Racer with id %s is not" +
                    " already in DB to be updated", racer.getId()));
        }

        racerTable.replace(racer.getId(), racer);
    }

    @Override
    public boolean deleteRacerById(Integer id) {
        return (racerTable.remove(id) != null);
    }

    @Override
    public Optional<Racer> getRacerById(Integer id) {
        return Optional.ofNullable(racerTable.get(id));
    }

    public List<Racer> getAllRacers() {
        return racerTable.values().stream().toList();
    }
}
