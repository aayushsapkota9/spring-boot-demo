package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello hello  dear %s!", name);
	}

	@PostMapping("/data")
	public Map<String, String> receiveData(@RequestBody String payload) {
		// This line prints to your server's console/logs
		System.out.println(payload);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Data received successfully");
		response.put("failedMsg", "Data not received successfully");

		return response;
	}
}
