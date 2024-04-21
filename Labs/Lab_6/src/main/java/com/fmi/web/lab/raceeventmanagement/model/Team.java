package com.fmi.web.lab.raceeventmanagement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @NotNull(message = "Team: name can't be null")
    @NotBlank(message = "Team: name need to have minimum 1 non-white space character")
    private String name;
    //@NotNull(message = "TeamDTO: racers can't be null")
    @Valid
    private List<Racer> racers;

    /*
    public Team(String name) {
        this.name = name;
        racers = new LinkedList<>(); // LinkedList because of add/delete operations,
        // that can be performed frequently
    }
    */
}
