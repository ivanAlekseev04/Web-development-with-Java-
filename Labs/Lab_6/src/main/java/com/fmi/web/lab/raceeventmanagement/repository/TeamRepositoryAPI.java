package com.fmi.web.lab.raceeventmanagement.repository;

import com.fmi.web.lab.raceeventmanagement.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepositoryAPI {

        /**
         * Add team to your DB. If the Team is already present throw Custom Exception
         * @param team
         */
        void addTeam(Team team);

        /**
         * Modify team from your DB
         * @param team
         */
        void updateTeam(Team team);

        /**
         * Delete team by name. If there is no element to be deleted then return false;
         * @param name
         * @return if there is element to delete -> true, if not -> false
         */
        boolean deleteTeamByName(String name);

        /**
         * Get team by passed name. If there is no element return Optional empty. The search is case-insensitive
         * @param name
         * @return Optional of Team
         */
        Optional<Team> getTeamByName(String name);

        /**
         * Get all teams
         * @return List of all teams
         */
        List<Team> getAllTeams();
}
