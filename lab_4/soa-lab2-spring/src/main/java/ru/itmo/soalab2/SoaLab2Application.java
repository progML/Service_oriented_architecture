package ru.itmo.soalab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SoaLab2Application {

	public static void main(String[] args) {
		SpringApplication.run(SoaLab2Application.class, args);
	}

}
