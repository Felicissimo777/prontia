package br.com.zyntra.prontia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.*;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProntiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProntiaApplication.class, args);
	}

}
