package entity;

import exceptions.AlreadyExistedException;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Team {
    private String name;
    private List<Racer> racers;

    public Team(String name) {
        this.name = name;
        racers = new LinkedList<>(); // LinkedList because of add/delete operations,
        // that can be performed frequently
    }

    public String getName() {
        return name;
    }

    public void addRacer(Racer racer) {
        if (isExisted(racer.getId())) {
            throw new AlreadyExistedException(String.format("Racer with id %s is already existed in team %s"
                    , racer.getId(), name));
        }

        racers.add(racer);
    }

    public boolean isExisted(Integer racerId) {
        return racers.stream().anyMatch(r -> Objects.equals(r.getId(), racerId));
    }

    public void deleteRacer(Integer id) {
        if (!isExisted(id)) {
            throw new NoSuchElementException(String.format("Racer with id %s isn't existed in team %s"
                    , id, name));
        }

        racers.removeIf(r -> Objects.equals(r.getId(), id));
    }

    public List<Racer> getRacers() {
        return racers;
    }
}
