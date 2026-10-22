package co.edu.univalle.demo_ds_univalle;

import org.springframework.boot.SpringApplication;

public class TestDemoDsUnivalleApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoDsUnivalleApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
