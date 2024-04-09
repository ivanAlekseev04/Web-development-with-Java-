package com.fmi.web.lab.raceeventmanagement.controller;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.repository.sequence.TeamSequence;
import com.fmi.web.lab.raceeventmanagement.service.EventService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/controller")
public class EventController {
    @Autowired
    EventService eventService;

    @PostConstruct
    public void initEventService() {
        // Create a test track
        Track testTrack = new Track("Speedway", 5000);
        testTrack.setId(1);

        // Create test teams and racers
        Racer racer1 = new Racer("John", "Doe", 28);
        Racer racer2 = new Racer("Jane", "Doe", 25);
        List<Racer> team1Racers = Arrays.asList(racer1, racer2);
        team1Racers.stream().forEach(team -> team.setId(TeamSequence.getNextValue()));

        Team team1 = new Team("Team Lightning", Arrays.asList(racer1, racer2));

        Racer racer3 = new Racer("Mike", "Smith", 30);
        Racer racer4 = new Racer("Sarah", "Jones", 27);
        List<Racer> team2Racers = Arrays.asList(racer3, racer4);
        team2Racers.stream().forEach(team -> team.setId(TeamSequence.getNextValue()));

        Team team2 = new Team("Team Thunder", Arrays.asList(racer3, racer4));

        // Prepare a list of teams for the event
        List<Team> testTeams = Arrays.asList(team1, team2);

        // Specify the date and time for the event
        LocalDateTime dateOfEvent = LocalDateTime.of(2024, 3, 21, 15, 0); // 21st March 2024, 3:00 PM

        // Create event
        eventService.createEvent("Spring Grand Prix", testTrack, testTeams, dateOfEvent);

        // Print all events
        //eventService.getAllEvents().stream().forEach(System.out::println);

        // get event after/before LocalDateTime.of(2023, 12, 1, 1, 1)
        //System.out.println(eventService.getAllEventsAfter(LocalDateTime.of(2023, 12, 1, 1, 1)));

        //System.out.println(eventService.getAllEventsBefore(LocalDateTime.of(2023, 12, 1, 1, 1)));

        //System.out.println(eventService.getAllRacersForNearestEvent());
    }

    @GetMapping("/events")
    public String getAllEvents() {
        return eventService.getAllEvents().toString();
    }

    @GetMapping("/racers")
    public String getAllRacersByCriteria(@RequestParam(required = false, name = "trackName") String trackName
            , @RequestParam(required = false, name = "event") String event) {

        if(trackName != null) {
            return eventService.getAllRacersByTrackName(trackName).toString();
        } else if(event != null) {
            return eventService.getAllRacersForNearestEvent().toString();
        }

        return Collections.emptyList().toString();
    }

    @GetMapping("/events/before")
    public String getAllEventsBefore(@RequestParam("year") Integer year, @RequestParam("month") Integer month
            , @RequestParam("day") Integer day, @RequestParam("hour") Integer hour
            , @RequestParam("minute") Integer minute) {

        try {
            return eventService.getAllEventsBefore(LocalDateTime.of(year, month, day, hour, minute))
                    .toString();
        } catch (DateTimeException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/events/after")
    public String getAllEventsAfter(@RequestParam("year") Integer year, @RequestParam("month") Integer month
            , @RequestParam("day") Integer day, @RequestParam("hour") Integer hour
            , @RequestParam("minute") Integer minute) {

        try {
            return eventService.getAllEventsAfter(LocalDateTime.of(year, month, day, hour, minute))
                    .toString();
        } catch (DateTimeException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/events/upcoming")
    public String getAllUpcomingEvents() {
        return eventService.getUpcomingEvents().toString();
    }

    @GetMapping("/events/track")
    public String getEventsForTrack(@RequestParam("trackId") Integer trackId) {
        if(trackId != null) {
            Track t = new Track("whatever", 0);
            t.setId(trackId);

            return eventService.getEventsForTrack(t).toString();
        }

        return Collections.emptyList().toString();
    }
}
