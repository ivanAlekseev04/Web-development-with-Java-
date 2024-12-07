package com.fmi.web.lab.raceeventmanagement.controller;

import com.fmi.web.lab.raceeventmanagement.mapper.RacerMapper;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.service.RacerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/racer-controller")
public class RacerController {
    @Autowired
    RacerMapper racerMapper;
    @Autowired
    RacerService racerService;

    @GetMapping("/racers")
    public ResponseEntity getAllRacersByCriteria(@RequestParam(value = "firstName", required = false) String firstName) {
        if(firstName == null) {
            return new ResponseEntity(racerService.getAllRacers()
                    .stream()
                    .map(r -> racerMapper.racerToDTO(r))
                    .toList(), HttpStatus.OK);
        }

        return new ResponseEntity(racerService.getAllRacersByFirstName(firstName)
                .stream()
                .map(r -> racerMapper.racerToDTO(r))
                .toList(), HttpStatus.OK);
    }

    @PostMapping("/racers")
    public ResponseEntity createRacer(@Valid @RequestBody Racer racer) {
        racer.setId(null);
        Racer created = racerService.createRacer(racer.getFirstName(), racer.getLastName(), racer.getAge());
        return new ResponseEntity(created, HttpStatus.CREATED);
    }

    @PatchMapping("/racers/{id}")
    public ResponseEntity updateRacer(@PathVariable("id") Integer id, @RequestBody Racer racer) {
        racer.setId(id);
        return new ResponseEntity(racerMapper.racerToDTO(racerService.updateRacer(racer)), HttpStatus.OK);
    }

    @DeleteMapping("/racers/{id}")
    public ResponseEntity deleteRacer(@PathVariable("id") Integer id) {
        if(!racerService.deleteRacerById(id)) {
            return new ResponseEntity(String.format("There is no racer with id %d", id)
                    , HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.noContent().build();
    }
}
