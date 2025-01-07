package com.fmi.raceeventmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
    @NotNull(message = "Team: name can't be null")
    @NotBlank(message = "Team: name need to have minimum 1 non-white space character")
    @Size(max = 60, message = "Team: Team's name need to be maximum of 60 characters length")
    private String name;

    @OneToMany(mappedBy = "team")
    private Set<Racer> racers;

    @ManyToMany(mappedBy = "teams")
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
