package com.fmi.raceeventmanagement.controller;

import com.fmi.raceeventmanagement.model.Racer;
import com.fmi.raceeventmanagement.model.Team;
import com.fmi.raceeventmanagement.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{name}")
    public ResponseEntity getTeamByName(@PathVariable("name") String name) {
        var toReturn = teamService.getTeamByName(name);

        return toReturn.map(team -> new ResponseEntity(toReturn.get(), HttpStatus.OK))
                    .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity getAllTeams() {
        return new ResponseEntity(teamService.getAllTeams(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createTeam(@Valid @RequestBody Team team) {
        return new ResponseEntity(teamService.createTeam(team.getName()), HttpStatus.CREATED);
    }

    @PatchMapping("/{name}")
    public ResponseEntity updateTeam(@PathVariable("name")String name, @RequestBody Team team) {
        team.setName(name);
        teamService.updateTeam(team);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteTeam(@PathVariable("name") String name) {
        teamService.deleteTeamByName(name);
        
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{name}/racers")
    public ResponseEntity addRacerToTeam(@PathVariable("name") String name, @Valid @RequestBody Racer racer) {
        teamService.addRacerToTeam(name, racer);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{name}/racers")
    public ResponseEntity deleteRacerFromTeamById(@PathVariable("name") String name, @RequestBody Integer id) {
        teamService.deleteRacerFromTeamById(name, id);
        
        return ResponseEntity.ok().build();
    }
}