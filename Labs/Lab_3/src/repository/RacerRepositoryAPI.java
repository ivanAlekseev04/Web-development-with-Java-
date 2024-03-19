package repository;

import entity.Racer;

import java.util.Optional;

public interface RacerRepositoryAPI {

        /**
         * Add racer to your DB
         * @param racer
         */
        void addRacer(Racer racer);

        /**
         * Modify racer from your DB
         * @param racer
         */
        void updateRacer(Racer racer);

        /**
         * Delete racer by id. If there is no element to be deleted then return false;
         * @param id
         * @return if there is element to delete -> true, if not -> false
         */
        boolean deleteRacerById(Integer id);

        /**
         * Get racer by passed id. If there is no element return Optional empty
         * @param id
         * @return Optional of Racer
         */
        Optional<Racer> getRacerById(Integer id);
}
