package co.edu.univalle.demo_ds_univalle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DemoDsUnivalleApplicationTests {

	@Test
	void contextLoads() {
	}

}
