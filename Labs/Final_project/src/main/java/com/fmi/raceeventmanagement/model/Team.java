package com.fmi.raceeventmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Team {

    @Id
    @NotNull(message = "Can't be null")
    @NotBlank(message = "Need to have minimum 1 non-white space character")
    @Size(max = 60, message = "Need to be maximum of 60 characters length")
    private String name;

    @OneToMany(mappedBy = "team")
    private Set<Racer> racers;

    @ManyToMany(mappedBy = "teams", cascade = {CascadeType.REMOVE})
    private Set<Event> events;

    public Team(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
