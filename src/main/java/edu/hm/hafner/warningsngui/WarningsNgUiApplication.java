package edu.hm.hafner.warningsngui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WarningsNgUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarningsNgUiApplication.class, args);
	}

}
