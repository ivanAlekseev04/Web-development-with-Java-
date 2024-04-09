package com.fmi.web.lab.raceeventmanagement.logger;

import com.fmi.web.lab.raceeventmanagement.vo.LoggerLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("local")
public class ConsoleLogger implements ILogger {
    @Value("${config.log.level.active}")
    private String logLevel;

    @Override
    public void info(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode() >= LoggerLevel.INFO.getCode()) {
            System.out.printf("%s [%s] - %s%n", LocalDateTime.now(), logLevel, toLog);
        }
    }

    @Override
    public void debug(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode() >= LoggerLevel.DEBUG.getCode()) {
            System.out.printf("%s [%s] - %s%n", LocalDateTime.now(), logLevel, toLog);
        }
    }

    @Override
    public void trace(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode().equals(LoggerLevel.TRACE.getCode())) {
            System.out.printf("%s [%s] - %s%n", LocalDateTime.now(), logLevel, toLog);
        }
    }

    @Override
    public void error(Object toLog) {
        if(LoggerLevel.valueOf(logLevel).getCode() >= LoggerLevel.ERROR.getCode()) {
            var thrownFrom = Thread.currentThread().getStackTrace()[2];

            System.out.printf("%s [%s] [%s.%s] - %s%n", LocalDateTime.now(),
                    logLevel, thrownFrom.getClassName(), thrownFrom.getMethodName(), toLog);
        }
    }
}
