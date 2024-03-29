package com.example.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.cursomc.services.DBService;
import com.example.cursomc.services.EmailService;
import com.example.cursomc.services.MockEmailService;

/**
 * Configuração personalizada de quando este profile estiver ativo
 */
@Configuration
@Profile("test")
public class TestConfig
{
	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException 
	{
		//inicia o banco com alguns dados preenchidos
		dbService.instantiateTestDataBase();
		
		return true;
	}
	
	/*Instanciado pelo autowired do spring*/
	@Bean
	public EmailService emailService() 
	{
		return new MockEmailService();
	}
}
