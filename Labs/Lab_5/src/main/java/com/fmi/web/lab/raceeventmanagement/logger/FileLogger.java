package com.fmi.web.lab.raceeventmanagement.logger;

import com.fmi.web.lab.raceeventmanagement.vo.LoggerLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
@Profile("dev")
public class FileLogger implements ILogger {
    @Value("${config.log.level.active}")
    private String logLevel;

    @Override
    public void info(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode() >= LoggerLevel.INFO.getCode()) {
            logInformation(toLog, LoggerLevel.valueOf(logLevel));
        }
    }

    @Override
    public void debug(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode() >= LoggerLevel.DEBUG.getCode()) {
            logInformation(toLog, LoggerLevel.valueOf(logLevel));
        }
    }

    @Override
    public void trace(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode().equals(LoggerLevel.TRACE.getCode())) {
            logInformation(toLog, LoggerLevel.valueOf(logLevel));
        }
    }

    @Override
    public void error(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode() >= LoggerLevel.ERROR.getCode()) {
            logInformation(toLog, LoggerLevel.valueOf(logLevel));
        }
    }

    private void logInformation(Object toLog, LoggerLevel logLevel) {
        File log = new File("log.txt");
        try (PrintWriter out = new PrintWriter(new FileWriter(log, true))) {
            out.println(String.format("%s [%s] - %s", LocalDateTime.now(), logLevel, toLog));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
