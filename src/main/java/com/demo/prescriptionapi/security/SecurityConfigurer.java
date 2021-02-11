package com.demo.prescriptionapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrescriptionApiUserDetailsService prescriptionApiUserDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(prescriptionApiUserDetailsService);
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth
		.inMemoryAuthentication()
		// USER is for end user
        .withUser("user").password(encoder.encode("user")).roles("USER")
        .and()
         // ADMIN is for Administrators
        .withUser("admin").password(encoder.encode("admin")).roles("ADMIN")
        .and()
        // SYSTEM is all internal calls
        .withUser("system").password(encoder.encode("system")).roles("SYSTEM")
        ;
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.httpBasic()
        	.and()
        	.authorizeRequests()
        	.antMatchers(HttpMethod.POST,"/drugs/validate").hasRole("SYSTEM")
        	.antMatchers(HttpMethod.POST,"/patients/validate").hasRole("SYSTEM")
        	.antMatchers(HttpMethod.POST,"/drugs/save").hasRole("ADMIN")
        	.antMatchers(HttpMethod.POST,"/patients/save").hasRole("ADMIN")
        	.antMatchers(HttpMethod.GET,"/patients/id/*").hasRole("SYSTEM")
        	.antMatchers(HttpMethod.GET,"/drugs/id/*").hasRole("SYSTEM")
        	.antMatchers(HttpMethod.POST,"/prescriptions/save").hasRole("USER")
        	.antMatchers(HttpMethod.GET,"/prescriptions/reference/*").hasRole("USER")
        	.and()
        	.csrf().disable();
    }
}
