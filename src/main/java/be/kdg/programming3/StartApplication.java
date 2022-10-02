package be.kdg.programming3;

import be.kdg.programming3.view.View;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication

public class StartApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StartApplication.class, args);
		context.getBean(View.class).show();
		context.close();
	}
}
