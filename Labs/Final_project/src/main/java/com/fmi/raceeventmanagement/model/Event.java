package com.fmi.raceeventmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
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

    @OneToOne
    @JoinColumn(name = "track_id")
    private Track track;
    
    @ManyToMany
    @JoinTable(
            name = "Event_Team",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")}
    )
    private Set<Team> teams;

    @NotNull(message = "Event: dateOfEvent can't be null")
    private LocalDateTime dateOfEvent;

    public Event(String name, Track track, LocalDateTime dateOfEvent) {
        this.name = name;
        this.track = track;
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
