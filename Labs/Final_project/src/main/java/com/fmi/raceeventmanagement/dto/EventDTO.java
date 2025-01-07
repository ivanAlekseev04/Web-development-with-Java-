package com.fmi.raceeventmanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class EventDTO {

    private Integer id;

    @NotNull(message = "EventDTO: name can't be null")
    @NotBlank(message = "EventDTO: name need to have minimum 1 non-white space character")
    private String name;

    @NotNull(message = "EventDTO: track can't be null")
    @Valid
    private TrackDTO track;

    @NotNull(message = "EventDTO: teams can't be null")
    @Valid
    private List<TeamDTO> teams;

    @NotNull(message = "EventDTO: dateOfEvent can't be null")
    private LocalDateTime dateOfEvent;
}
