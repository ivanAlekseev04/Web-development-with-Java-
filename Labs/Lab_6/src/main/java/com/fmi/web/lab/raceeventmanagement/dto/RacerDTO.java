package com.fmi.web.lab.raceeventmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RacerDTO {
    @NotNull(message = "RacerDTO: firstName can't be null")
    @NotBlank(message = "RacerDTO: firstName need to have minimum 1 non-white space character")
    private String firstName;

    @NotNull(message = "RacerDTO: lastName can't be null")
    @NotBlank(message = "RacerDTO: lastName need to have minimum 1 non-white space character")
    private String lastName;

    @NotNull(message = "RacerDTO: age can't be null")
    @Min(value = 0, message = "RacerDTO: age can't be negative")
    private Integer age;
}
