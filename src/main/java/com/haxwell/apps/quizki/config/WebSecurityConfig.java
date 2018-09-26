package com.haxwell.apps.quizki.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private AuthenticationEntryPoint auth;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select name,password,enabled from user where name=?")
		.authoritiesByUsernameQuery(
			"select u.name, ur.name "
			+ "from user u, user_role ur "
			+ "where u.name=? "
			+ "and u.user_role_id = ur.id"
			)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().authenticationEntryPoint(auth)
        	.and()
        	.csrf()
            	.disable()
        	.authorizeRequests()
    		.antMatchers(HttpMethod.POST, "/api/users").permitAll()
    		.antMatchers(HttpMethod.POST, "/api/users/isUnique").permitAll()
    		.antMatchers(HttpMethod.OPTIONS, "/api/verifyCredentials").permitAll()
    		.antMatchers(HttpMethod.GET, "/api/roles").permitAll()
	    		// TODO: Handle login.. That should be open.
	    		.antMatchers("/api/**").authenticated()
                .and() 
                .logout()
                .permitAll();
    }
	
}
