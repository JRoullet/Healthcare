package jroullet.mspatient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPatientApplication.class, args);
	}

}
