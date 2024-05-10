package com.fmi.web.lab.raceeventmanagement.controller;

import com.fmi.web.lab.raceeventmanagement.mapper.RacerMapper;
import com.fmi.web.lab.raceeventmanagement.mapper.TeamMapper;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.service.TeamService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.util.Collections;

@RestController
@RequestMapping("/team-controller")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    RacerMapper racerMapper;

    @GetMapping("/teams")
    public ResponseEntity getAllTeamsByCriteria(@RequestParam(value = "name", required = false) String name) {
        if(name != null) {
            var toReturn = teamService.getTeamByName(name);

            return toReturn.map(team -> ResponseEntity.noContent().build())
                    .orElseGet(() -> new ResponseEntity(toReturn.get(), HttpStatus.FOUND));
        }

        return new ResponseEntity(teamService.getAllTeams()
                .stream()
                .map(t -> teamMapper.teamToDTO(t))
                .toList(), HttpStatus.OK);
    }

    @PostMapping("/teams")
    public ResponseEntity createTeam(@Valid @RequestBody Team team) {
        var created = teamService.createTeam(team.getName(), team.getRacers());

        return new ResponseEntity(teamMapper.teamToDTO(created), HttpStatus.CREATED);
    }

    @PatchMapping("/teams/{name}")
    public ResponseEntity updateTeam(@PathVariable("name")String name, @RequestBody Team team) {
        team.setName(name);
        return new ResponseEntity(teamMapper.teamToDTO(teamService.updateTeam(team)), HttpStatus.OK);
    }

    @DeleteMapping("/teams/{name}")
    public ResponseEntity deleteTeam(@PathVariable("name") String name) {
        teamService.deleteTeamByName(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/teams/racers/{name}")
    public ResponseEntity addRacerToTeam(@PathVariable("name") String name, @Valid @RequestBody Racer racer) {
        return new ResponseEntity(teamService.addRacerToTeam(name, racer)
                .stream()
                .map(r -> racerMapper.racerToDTO(r))
                .toList(), HttpStatus.OK);
    }

    @DeleteMapping("/teams/racers/{name}")
    public ResponseEntity deleteRacerFromTeamById(@PathVariable("name") String name, @RequestBody Integer id) {
        return new ResponseEntity(teamService.deleteRacerFromTeamById(name, id)
                .stream()
                .map(r -> racerMapper.racerToDTO(r))
                .toList(), HttpStatus.OK);
    }
}
