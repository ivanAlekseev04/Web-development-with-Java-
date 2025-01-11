package com.fmi.raceeventmanagement.controller;

import com.fmi.raceeventmanagement.dto.RacerDTO;
import com.fmi.raceeventmanagement.model.Racer;
import com.fmi.raceeventmanagement.service.RacerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/racer")
@RequiredArgsConstructor
public class RacerController {
    
    private final RacerService racerService;

    @GetMapping
    public ResponseEntity getAllRacersByCriteria(@RequestParam(value = "firstName", required = false) String firstName) {
        if(firstName == null) {
            return new ResponseEntity(racerService.getAllRacers(), HttpStatus.OK);
        }

        return new ResponseEntity(racerService.getAllRacersByFirstName(firstName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createRacer(@Valid @RequestBody RacerDTO racer) {
        return new ResponseEntity(racerService.createRacer(racer.getFirstName(), racer.getLastName(), racer.getAge()),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateRacer(@PathVariable("id") Integer id, @RequestBody Racer racer) {
        racer.setId(id);
        racerService.updateRacer(racer);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRacer(@PathVariable("id") Integer id) {
        racerService.deleteRacerById(id);

        System.out.println("Racer with id " + id + " need to be deleted!");

        return ResponseEntity.ok().build();
    }
}
