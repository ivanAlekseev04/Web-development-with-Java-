package com.fmi.raceeventmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrackDTO {

    private Integer id;

    @NotNull(message = "TrackDTO: name can't be null")
    @NotBlank(message = "TrackDTO: name need to have minimum 1 non-white space character")
    private String name;

    @NotNull(message = "TrackDTO: length can't be null")
    @Min(value = 0, message = "TrackDTO: length can't be negative")
    private Integer length;
}
