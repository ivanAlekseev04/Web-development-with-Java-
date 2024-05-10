package com.fmi.web.lab.raceeventmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    Team team;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Racer(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Racer racer = (Racer) o;
        return Objects.equals(id, racer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
