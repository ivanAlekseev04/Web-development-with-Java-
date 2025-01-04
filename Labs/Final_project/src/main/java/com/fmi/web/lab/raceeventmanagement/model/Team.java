package com.fmi.web.lab.raceeventmanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
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

    @NotNull(message = "Team: racers can't be null")
    @Valid
    @OneToMany(mappedBy = "team")
    private Set<Racer> racers;

    @ManyToMany(mappedBy = "teams")
    private Set<Event> events;

    public Team(String name, Set<Racer> racers) {
        this.name = name;
        this.racers = racers;
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
