package com.alura.literaluraweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LiteralurawebApplication implements CommandLineRunner {

	private final MenuService menuService;
	public LiteralurawebApplication(MenuService menuService) {
		this.menuService = menuService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteralurawebApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {
		menuService.showMenu();
	}
}
