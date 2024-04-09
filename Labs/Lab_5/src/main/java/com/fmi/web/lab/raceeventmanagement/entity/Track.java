package com.fmi.web.lab.raceeventmanagement.entity;

public class Track {
    private Integer id;
    private String name;
    private Integer length;

    public Track(String name, Integer length) {
        this.name = name;
        this.length = length;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
