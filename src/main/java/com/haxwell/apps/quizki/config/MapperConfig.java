package com.haxwell.apps.quizki.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class MapperConfig {

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper mm = new ModelMapper();
		
		//add configurations here
		System.out.println("The modelMapper is being configured here...");
		
		mm.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
			.setDestinationNamingConvention(NamingConventions.JAVABEANS_ACCESSOR);

		return mm;
	}
}
