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
public class Racer {
    @NotNull(message = "Racer: firstName can't be null")
    @NotBlank(message = "Racer: firstName need to have minimum 1 non-white space character")
    private String firstName;
    @NotNull(message = "Racer: lastName can't be null")
    @NotBlank(message = "Racer: lastName need to have minimum 1 non-white space character")
    private String lastName;
    @NotNull(message = "Racer: age can't be null")
    @Min(value = 0, message = "Racer: age can't be negative")
    private Integer age;
    private Integer id;

    public Racer(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
