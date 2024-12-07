package com.fmi.web.lab.raceeventmanagement.repository.sequence;

public class EventSequence {
    private static Integer sequence = 0;

    public static Integer getNextValue() {
        return sequence++;
    }
}
