package jroullet.msnotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNotesApplication.class, args);
	}

}
