package com.fmi.web.lab.raceeventmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(id, track.id) && Objects.equals(name, track.name) && Objects.equals(length, track.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, length);
    }
}
