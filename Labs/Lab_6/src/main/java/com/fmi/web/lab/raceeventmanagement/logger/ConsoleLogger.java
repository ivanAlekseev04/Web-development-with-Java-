package com.fmi.web.lab.raceeventmanagement.logger;

import com.fmi.web.lab.raceeventmanagement.vo.LoggerLevel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Component
@Profile("local")
public class ConsoleLogger implements ILogger {
    @Value("${config.log.level.active}")
    private String definedLogLevel;
    private LoggerLevel logLevel;

    @PostConstruct
    private void initLogLevel() {
        logLevel = LoggerLevel.valueOf(definedLogLevel);
    }

    @Override
    public void info(Object toLog) {
        if(logLevel.getCode() >= LoggerLevel.INFO.getCode()) {
            System.out.printf("%s [%s] - %s%n", LocalDateTime.now(), logLevel, toLog);
        }
    }

    @Override
    public void debug(Object toLog) {
        if(logLevel.getCode() >= LoggerLevel.DEBUG.getCode()) {
            System.out.printf("%s [%s] - %s%n", LocalDateTime.now(), logLevel, toLog);
        }
    }

    @Override
    public void trace(Object toLog) {
        if(logLevel.getCode().equals(LoggerLevel.TRACE.getCode())) {
            System.out.printf("%s [%s] - %s%n", LocalDateTime.now(), logLevel, toLog);
        }
    }

    @Override
    public void error(Object toLog) {
        if(logLevel.getCode() >= LoggerLevel.ERROR.getCode()) {
            var thrownFrom = Thread.currentThread().getStackTrace()[2];

            System.out.printf("%s [%s] [%s.%s] - %s%n", LocalDateTime.now(),
                    logLevel, thrownFrom.getClassName(), thrownFrom.getMethodName(), toLog);
        }
    }
}
