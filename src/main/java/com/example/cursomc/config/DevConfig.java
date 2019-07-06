package com.example.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.cursomc.services.DBService;

/**
 * Configuração personalizada de quando este profile estiver ativo
 */
@Configuration
@Profile("dev")
public class DevConfig
{
	@Autowired
	private DBService dbService;
	
	//obtem a estrategia de geração do banco
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException 
	{
		//se a estrategia de geração não for create
		if (!"create".equals(strategy))
		{
			//para o metodo aqui e não realiza a instrução dos inserts abaixo
			return false;
		}
		
		//inicia o banco com alguns dados preenchidos
		dbService.instantiateTestDataBase();
		
		return true;
	}
}
