package com.fmi.raceeventmanagement.controller;

import com.fmi.raceeventmanagement.dto.TrackDTO;
import com.fmi.raceeventmanagement.model.Track;
import com.fmi.raceeventmanagement.service.TrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/track")
public class TrackController {

    private final TrackService trackService;

    @GetMapping
    public ResponseEntity getAllTracksByCriteria(@RequestParam(value = "name", required = false) String name) {
        if(name == null) {
            return new ResponseEntity(trackService.getAllTracks(), HttpStatus.OK);
        }

        return new ResponseEntity(trackService.getAllTracksByName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createTrack(@Valid @RequestBody TrackDTO track) {
        return new ResponseEntity(trackService.createTrack(track.getName(), track.getLength()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateTrack(@PathVariable("id") Integer id, @RequestBody Track track) {
        track.setId(id);
        trackService.updateTrack(track);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteTrack(@PathVariable("name") String name) {
        trackService.deleteTrackByName(name);

        return ResponseEntity.noContent().build();
    }
}