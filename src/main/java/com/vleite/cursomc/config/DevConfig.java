package com.vleite.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vleite.cursomc.services.DbService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	DbService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	String dataBaseStrategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if ("create".equals(dataBaseStrategy)) {
			dbService.instantiateTestDatabase();
		}
		return true;
	}

}
