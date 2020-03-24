package com.vleite.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vleite.cursomc.services.DbService;
import com.vleite.cursomc.services.MockEmailService;
import com.vleite.cursomc.services.interfaces.IEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	DbService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public IEmailService emailService() {
		return new MockEmailService();
	}
	
}
