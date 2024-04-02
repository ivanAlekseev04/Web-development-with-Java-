package com.fmi.web.fmiWebDemo;

import com.fmi.web.fmiWebDemo.service.RacerService;
import com.fmi.web.fmiWebDemo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.stream.Stream;

@SpringBootApplication
public class FmiWebDemoApplication implements CommandLineRunner {
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

		racerService.createRacer("Ivan", "Alekseev", 19);
		racerService.createRacer("Cveti", "Gotsova", 20);
		racerService.createRacer("Nadya", "Koykova", 20);
		racerService.createRacer("George", "Dzhanavarov", 20);

		racerService.getAllRacers().forEach(el -> System.out.println(el));
	}

	public static void main(String[] args) {
		SpringApplication.run(FmiWebDemoApplication.class, args);
	}
}
