package com.fmi.raceeventmanagement.dto;

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

    @NotNull(message = "Can't be null")
    @NotBlank(message = "Need to have minimum 1 non-white space character")
    @Size(max = 60, message = "Need to be maximum of 60 characters length")
    private String name;

    @NotNull(message = "Can't be null")
    @Valid
    private List<RacerDTO> racers;
}
