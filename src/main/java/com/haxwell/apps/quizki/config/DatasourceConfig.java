package com.haxwell.apps.quizki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.haxwell.apps.quizki.*" }) // TODO: what is this? and is the regex tight enough?
public class DatasourceConfig {

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/quizki2_db?verifyServerCertificate=false&useSSL=true");
	    driverManagerDataSource.setUsername("quizki2");
	    driverManagerDataSource.setPassword("quizki2");
	    return driverManagerDataSource;
	}
}
