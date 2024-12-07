package com.fmi.web.lab.raceeventmanagement.dto;

import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventDTO {
    @NotNull(message = "EventDTO: name can't be null")
    @NotBlank(message = "EventDTO: name need to have minimum 1 non-white space character")
    String name;

    //@NotNull(message = "EventDTO: track can't be null")
    @Valid
    TrackDTO track;

    //@NotNull(message = "EventDTO: teams can't be null")
    @Valid
    List<TeamDTO> teams;

    @NotNull(message = "EventDTO: dateOfEvent can't be null")
    LocalDateTime dateOfEvent;
}
