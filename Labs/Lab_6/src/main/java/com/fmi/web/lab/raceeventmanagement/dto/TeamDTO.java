package com.fmi.web.lab.raceeventmanagement.dto;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TeamDTO {
    @NotNull(message = "TeamDTO: name can't be null")
    @NotBlank(message = "TeamDTO: name need to have minimum 1 non-white space character")
    String name;

    //@NotNull(message = "TeamDTO: racers can't be null")
    @Valid
    List<RacerDTO> racers;
}
