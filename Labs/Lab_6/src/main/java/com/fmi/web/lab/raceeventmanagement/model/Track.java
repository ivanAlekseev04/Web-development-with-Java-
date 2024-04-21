package com.fmi.web.lab.raceeventmanagement.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    private Integer id;
    @NotNull(message = "Track: name can't be null")
    @NotBlank(message = "Track: name need to have minimum 1 non-white space character")
    private String name;
    @NotNull(message = "Track: length can't be null")
    @Min(value = 0, message = "Track: length can't be negative")
    private Integer length;

    public Track(String name, Integer length) {
        this.name = name;
        this.length = length;
    }
}
