package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.exceptions.AlreadyExistedException;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.repository.sequence.RaceSequence;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class RacerRepository {
    private Map<Integer, Racer> racerTable = new HashMap<>();

    /**
     * Add racer to your DB
     * @param racer
     */
    public Racer addRacer(Racer racer) {
        if (racer.getId() != null) {
            throw new AlreadyExistedException("On creation step Racer id need to be null");
        }

        racer.setId(RaceSequence.getNextValue());
        racerTable.put(racer.getId(), racer);
        return racer;
    }

    /**
     * Modify racer from your DB
     * @param racer
     */
    public Racer updateRacer(Racer racer) {
        if (!racerTable.containsKey(racer.getId())) {
            throw new NoSuchElementException(String.format("Racer with id %s is not" +
                    " already in DB to be updated", racer.getId()));
        }

        Racer toModify = racerTable.get(racer.getId());

        if (racer.getFirstName() != null) {
            toModify.setFirstName(racer.getFirstName());
        }
        if(racer.getLastName() != null) {
            toModify.setLastName(racer.getLastName());
        }
        if(racer.getAge() != null && racer.getAge() > 0) {
            toModify.setAge(racer.getAge());
        }

        return toModify;
    }

    /**
     * Delete racer by id. If there is no element to be deleted then return false;
     * @param id
     * @return if there is element to delete -> true, if not -> false
     */
    public boolean deleteRacerById(Integer id) {
        return (racerTable.remove(id) != null);
    }

    /**
     * Get racer by passed id. If there is no element return Optional empty
     * @param id
     * @return Optional of Racer
     */
    public Optional<Racer> getRacerById(Integer id) {
        return Optional.ofNullable(racerTable.get(id));
    }

    public List<Racer> getAllRacers() {
        return racerTable.values().stream().toList();
    }
}
