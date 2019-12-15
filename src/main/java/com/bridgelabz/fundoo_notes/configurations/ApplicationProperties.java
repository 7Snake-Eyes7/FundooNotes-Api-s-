package com.bridgelabz.fundoo_notes.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationProperties {

	@Bean
	public BCryptPasswordEncoder getPasswordEncryption() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper getModelMapper()
	{
		return new ModelMapper();
	}

}
