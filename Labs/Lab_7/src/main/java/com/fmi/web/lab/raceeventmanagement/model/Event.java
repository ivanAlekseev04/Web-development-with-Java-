package com.fmi.web.lab.raceeventmanagement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Integer id;
    @NotNull(message = "Event: name can't be null")
    @NotBlank(message = "Event: name need to have minimum 1 non-white space character")
    private String name;
    @NotNull(message = "Event: track can't be null")
    @Valid
    private Track track;
    @NotNull(message = "Event: teams can't be null")
    @Valid
    private List<Team> teams;
    @NotNull(message = "Event: dateOfEvent can't be null")
    private LocalDateTime dateOfEvent;

    public Event(String name, Track track, List<Team> teams, LocalDateTime dateOfEvent) {
        this.name = name;
        this.track = track;
        this.teams = teams;
        this.dateOfEvent = dateOfEvent;
    }
}
