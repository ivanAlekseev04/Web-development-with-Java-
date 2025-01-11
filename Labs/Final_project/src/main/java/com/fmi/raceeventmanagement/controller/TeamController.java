package com.fmi.raceeventmanagement.controller;

import com.fmi.raceeventmanagement.model.Racer;
import com.fmi.raceeventmanagement.model.Team;
import com.fmi.raceeventmanagement.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity getTeamByName(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return new ResponseEntity(teamService.getAllTeams(), HttpStatus.OK);
        }

        var toReturn = teamService.getTeamByName(name);
        return toReturn.map(team -> new ResponseEntity(List.of(toReturn.get()), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity(new ArrayList(), HttpStatus.OK));
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

    @PostMapping("/{name}/racers/{racerId}")
    public ResponseEntity addRacerToTeam(@PathVariable("name") String name,
                                         @PathVariable("racerId") Integer racerId) {
        teamService.addRacerToTeam(name, racerId);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{name}/racers/{racerId}")
    public ResponseEntity deleteRacerFromTeamById(@PathVariable("name") String name,
                                                  @PathVariable("racerId") Integer racerId) {
        teamService.deleteRacerFromTeamById(name, racerId);
        
        return ResponseEntity.ok().build();
    }
}