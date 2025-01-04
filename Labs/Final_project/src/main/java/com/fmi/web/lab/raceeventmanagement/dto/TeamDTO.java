package com.fmi.web.lab.raceeventmanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TeamDTO {
    @NotNull(message = "TeamDTO: name can't be null")
    @NotBlank(message = "TeamDTO: name need to have minimum 1 non-white space character")
    @Size(max = 60, message = "TeamDTO: Team's name need to be maximum of 60 characters length")
    String name;

    @NotNull(message = "TeamDTO: racers can't be null")
    @Valid
    List<RacerDTO> racers;
}
