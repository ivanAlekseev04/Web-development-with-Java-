package com.fmi.raceeventmanagement.controller;

import com.fmi.raceeventmanagement.model.Event;
import com.fmi.raceeventmanagement.model.Team;
import com.fmi.raceeventmanagement.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity getAllEvents() {
        return new ResponseEntity(eventService.getAllEvents(), HttpStatus.OK);
    }
    
    @PostMapping("/track/{trackId}")
    public ResponseEntity createEvent(@PathVariable("trackId") Integer trackId, @Valid @RequestBody Event event) {
        return new ResponseEntity(eventService.createEvent(event.getName(), trackId, event.getDateOfEvent()),
                HttpStatus.CREATED);
    }

    @GetMapping("/track/{trackName}/racers")
    public ResponseEntity getAllRacersForTheEventsOnStatedTrack(@PathVariable(name = "trackName") String trackName) {
        return new ResponseEntity(eventService.getAllRacersByTrackName(trackName), HttpStatus.OK);
    }

    @GetMapping("/nearest/racers")
    public ResponseEntity getAllRacersForTheNearestEvent() {
        return new ResponseEntity(eventService.getAllRacersForNearestEvent(), HttpStatus.OK);
    }

    @GetMapping("/before/{dateTime}")
    public ResponseEntity getAllEventsBefore(@PathVariable("dateTime") LocalDateTime dateTime) {
        return new ResponseEntity(eventService.getAllEventsBefore(dateTime), HttpStatus.OK);
    }

    @GetMapping("/after/{dateTime}")
    public ResponseEntity getAllEventsAfter(@PathVariable("dateTime") LocalDateTime dateTime) {
        return new ResponseEntity(eventService.getAllEventsAfter(dateTime), HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity getAllUpcomingEvents() {
        return new ResponseEntity(eventService.getUpcomingEvents(), HttpStatus.OK);
    }

    @GetMapping("/track/{trackId}")
    public ResponseEntity getEventsForTrack(@PathVariable("trackId") Integer trackId) {
        return new ResponseEntity(eventService.getEventsForTrack(trackId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEventById(@PathVariable("id") Integer id) {
        eventService.deleteEventById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") Integer id, @RequestBody Event event) {
        event.setId(id);
        eventService.updateEvent(event);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{eventId}/teams")
    public ResponseEntity addTeamToEvent(@PathVariable("eventId") Integer eventId, @Valid @RequestBody Team team) {
        eventService.addTeamToEvent(eventId, team);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventId}/teams/{teamName}")
    public ResponseEntity deleteTeamFromEventByName(@PathVariable("eventId") Integer eventId,
                                                    @PathVariable("teamName") String teamName) {
        eventService.deleteTeamFromEventByName(eventId, teamName);

        return ResponseEntity.ok().build();
    }
}

//    @PostConstruct
//    public void initEventService() {
//        Track testTrack1 = new Track("Speedway", 5000);
//        Track testTrack2 = new Track("Speedway2", 50000);
//        trackRepository.save(testTrack1);
//        trackRepository.save(testTrack2);
//
//        Racer racer1 = new Racer("John", "Doe", 28);
//        Racer racer2 = new Racer("Jane", "Doe", 25);
//        List<Racer> team1Racers = new LinkedList<>(Arrays.asList(racer1, racer2));
//        team1Racers.stream().forEach(team -> team.setId(1));
//
//        Team team1 = new Team("Team Lightning", Arrays.asList(racer1, racer2));
//
//        Racer racer3 = new Racer("Mike", "Smith", 30);
//        Racer racer4 = new Racer("Sarah", "Jones", 27);
//        List<Racer> team2Racers = new LinkedList<>(Arrays.asList(racer3, racer4));
//        team2Racers.stream().forEach(team -> team.setId(2));
//
//        Team team2 = new Team("Team Thunder", Arrays.asList(racer3, racer4));
//
//        List<Team> testTeams = new LinkedList<>(Arrays.asList(team1, team2));
//
//        LocalDateTime dateOfEvent = LocalDateTime.of(2024, 3, 21, 15, 0); // 21st March 2024, 3:00 PM
//        LocalDateTime dateOfEvent2 = LocalDateTime.of(2024, 4, 19, 15, 0);
//
//        eventService.createEvent("Spring Grand Prix", testTrack1, testTeams, dateOfEvent);
//        eventService.createEvent("Spring Grand Prix 2", testTrack2, new LinkedList<>(testTeams), dateOfEvent2);
//    }
