package service;

import entity.Racer;
import repository.RacerRepository;
import validator.StringValidator;

import java.util.List;

public class RacerService implements RacerServiceAPI {

    @Override
    public List<Racer> getAllRacers() {
        return RacerRepository.getInstance().getAllRacers();
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

        RacerRepository.getInstance().addRacer(new Racer(firstName, lastName, age));
    }

    @Override
    public void updateRacer(Racer racer) {
        if (racer == null) {
            throw new IllegalArgumentException("Racer cannot be null");
        }

        validateRacerAttributes(racer.getFirstName(), racer.getLastName(), racer.getAge());
        // Assume that we can update everything except the "id"
        RacerRepository.getInstance().updateRacer(racer);

    }

    @Override
    public boolean deleteRacerById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return RacerRepository.getInstance().deleteRacerById(id);
    }

    private void validateRacerAttributes(String firstName, String lastName, Integer age) {
        StringValidator.validate(firstName, "firstName");
        StringValidator.validate(lastName, "lastName");

        if (age == null || age <= 0) {
            throw new IllegalArgumentException("Racer's age cannot be null/less or equal to 0");
        }
    }
}
