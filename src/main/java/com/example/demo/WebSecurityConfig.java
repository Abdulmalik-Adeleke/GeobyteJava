package com.example.demo;

import com.example.demo.Auth.Token.TokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private TokenFilter tokenFilter;
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
            .cors();
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
			    .authorizeRequests()
				.antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/staff/*").hasRole("STAFF")
                .antMatchers("/auth/*").permitAll()
                .and().addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);


	}

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}