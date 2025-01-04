package com.fmi.web.lab.raceeventmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Event: name can't be null")
    @NotBlank(message = "Event: name need to have minimum 1 non-white space character")
    private String name;

    @NotNull(message = "Event: track can't be null")
    @Valid
    @OneToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @NotNull(message = "Event: teams can't be null")
    @Valid
    @ManyToMany
    @JoinTable(
            name = "Event_Team",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")}
    )
    private Set<Team> teams;

    @NotNull(message = "Event: dateOfEvent can't be null")
    private LocalDateTime dateOfEvent;

    public Event(String name, Track track, Set<Team> teams, LocalDateTime dateOfEvent) {
        this.name = name;
        this.track = track;
        this.teams = teams;
        this.dateOfEvent = dateOfEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name) && Objects.equals(track, event.track) && Objects.equals(dateOfEvent, event.dateOfEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, track, dateOfEvent);
    }
}
