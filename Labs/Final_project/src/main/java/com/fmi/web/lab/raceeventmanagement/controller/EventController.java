package com.fmi.web.lab.raceeventmanagement.controller;

import com.fmi.web.lab.raceeventmanagement.mapper.EventMapper;
import com.fmi.web.lab.raceeventmanagement.mapper.RacerMapper;
import com.fmi.web.lab.raceeventmanagement.mapper.TeamMapper;
import com.fmi.web.lab.raceeventmanagement.model.Event;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/event-controller")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final RacerMapper racerMapper;
    private final TeamMapper teamMapper;

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

    @GetMapping("/events")
    public ResponseEntity getAllEvents() {
        return new ResponseEntity(eventService.getAllEvents()
                .stream().map(e -> eventMapper.eventToDTO(e))
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/racers")
    public ResponseEntity getAllRacersByCriteria(@RequestParam(required = false, name = "trackName") String trackName
            , @RequestParam(required = false, name = "event") String event) {

        if(trackName != null) {
            var toReturn = eventService.getAllRacersByTrackName(trackName)
                    .stream()
                    .map(r -> racerMapper.racerToDTO(r)).toList();

            return new ResponseEntity(toReturn, HttpStatus.OK);
        } else if(event != null && event.equals("nearest")) {
            var toReturn = eventService.getAllRacersForNearestEvent()
                    .stream()
                    .map(r -> racerMapper.racerToDTO(r)).toList();

            return new ResponseEntity(toReturn, HttpStatus.OK);
        }

        return new ResponseEntity(Collections.emptyList(), HttpStatus.OK);
    }

    @GetMapping("/events/before")
    public ResponseEntity getAllEventsBefore(@RequestParam("year") Integer year, @RequestParam("month") Integer month
            , @RequestParam("day") Integer day, @RequestParam("hour") Integer hour
            , @RequestParam("minute") Integer minute) {

        return new ResponseEntity(
                eventService.getAllEventsBefore(LocalDateTime.of(year, month, day, hour, minute))
                        .stream()
                        .map(e -> eventMapper.eventToDTO(e))
                        .toList(), HttpStatus.OK);
    }

    @GetMapping("/events/after")
    public ResponseEntity getAllEventsAfter(@RequestParam("year") Integer year, @RequestParam("month") Integer month
            , @RequestParam("day") Integer day, @RequestParam("hour") Integer hour
            , @RequestParam("minute") Integer minute) {

        return new ResponseEntity(
                eventService.getAllEventsAfter(LocalDateTime.of(year, month, day, hour, minute))
                        .stream()
                        .map(e -> eventMapper.eventToDTO(e))
                        .toList(), HttpStatus.OK);
    }

    @GetMapping("/events/upcoming")
    public ResponseEntity getAllUpcomingEvents() {
        return new ResponseEntity(eventService.getUpcomingEvents()
                .stream()
                .map(e -> eventMapper.eventToDTO(e))
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/events/track")
    public ResponseEntity getEventsForTrack(@RequestParam("trackId") Integer trackId) {
        if(trackId != null) {
            Track t = new Track("whatever", 0);
            t.setId(trackId);

            return new ResponseEntity(eventService.getEventsForTrack(t)
                    .stream()
                    .map(e -> eventMapper.eventToDTO(e))
                    .toList(), HttpStatus.OK);
        }

        return new ResponseEntity(Collections.emptyList(), HttpStatus.OK);
    }

    @PostMapping("/events")
    public ResponseEntity createEvent(@Valid @RequestBody Event event) {
        Event created = eventService.createEvent(event.getName(), event.getTrack(), event.getTeams(),
                event.getDateOfEvent());

        return new ResponseEntity(eventMapper.eventToDTO(created), HttpStatus.CREATED);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity deleteEventById(@PathVariable("id") Integer id) {
        eventService.deleteEventById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/events/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") Integer id, @RequestBody Event event) {
        event.setId(id);

        return new ResponseEntity(eventMapper.eventToDTO(eventService.updateEvent(event)), HttpStatus.OK);
    }

    @PostMapping("/events/teams/{eventId}")
    public ResponseEntity addTeamToEvent(@PathVariable("eventId") Integer eventId, @Valid @RequestBody Team team) {
        return ResponseEntity.ok(eventService.addTeamToEvent(eventId, team)
                .stream()
                .map(t -> teamMapper.teamToDTO(t))
                .toList());
    }

    @DeleteMapping("/events/teams/{eventId}")
    public ResponseEntity deleteTeamFromEventByName(@PathVariable("eventId") Integer eventId, @RequestBody String name) {
        return ResponseEntity.ok(eventService.deleteTeamFromEventByName(eventId, name)
                .stream()
                .map(t -> teamMapper.teamToDTO(t))
                .toList());
    }
}
