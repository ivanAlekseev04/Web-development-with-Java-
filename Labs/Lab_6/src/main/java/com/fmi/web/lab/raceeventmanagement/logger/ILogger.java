package com.fmi.web.lab.raceeventmanagement.logger;

public interface ILogger {

    void info(Object toLog);

    void debug(Object toLog);

    void trace(Object toLog);

    void error(Object toLog);
}
