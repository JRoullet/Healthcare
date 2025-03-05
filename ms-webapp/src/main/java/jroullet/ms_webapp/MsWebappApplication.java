package jroullet.ms_webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class MsWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsWebappApplication.class, args);
	}

}
