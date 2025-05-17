package ea.services.ea_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EaAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaAuthServiceApplication.class, args);
	}

}
