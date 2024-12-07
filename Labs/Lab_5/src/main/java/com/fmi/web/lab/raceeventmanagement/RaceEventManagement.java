package com.fmi.web.lab.raceeventmanagement;

import com.fmi.web.lab.raceeventmanagement.logger.ConsoleLogger;
import com.fmi.web.lab.raceeventmanagement.logger.FileLogger;
import com.fmi.web.lab.raceeventmanagement.logger.ILogger;
import com.fmi.web.lab.raceeventmanagement.service.RacerService;
import com.fmi.web.lab.raceeventmanagement.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class RaceEventManagement implements CommandLineRunner {
	@Autowired
	private ILogger logger;
	@Autowired
	private RacerService racerService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private TeamService teamService;

	@Override
	public void run(String... args) throws Exception {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		var myBeans = Stream.of(beanNames) // get all beans that were defined by me
				.map(n -> applicationContext.getBean(n).getClass().toString())
				.filter(bn -> bn.contains("com.fmi"))
				.toList();
		// Print only beans defined by me
		myBeans.forEach(System.out::println);

		System.out.println("---------------------------------------------------");

		// Print all loaded bean
		Stream.of(beanNames)
				.map(bean -> applicationContext.getBean(bean).getClass())
				.forEach(System.out::println);

		System.out.println("---------------------------------------------------");
		System.out.println("Some business logic");

		try {
			racerService.createRacer("Ivan", "Alekseev", 19);
			racerService.createRacer("Ivan", null, 19);
		} catch (RuntimeException e) {
			String stackTrace = Arrays.stream(e.getStackTrace())
					.map(st -> st.toString())
					.collect(Collectors.joining("\n"));

			logger.error(e.getMessage() + "\n" + stackTrace);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(RaceEventManagement.class, args);
	}
}
