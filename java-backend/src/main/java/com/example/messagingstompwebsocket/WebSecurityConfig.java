package com.example.messagingstompwebsocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@ComponentScan({ "com.example.messagingstompwebsocket" })
@Profile({ "!test" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers("/api/**").authenticated()
				.antMatchers("/api/sso/**").permitAll())
				.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder())));
	}

	@Bean
	JwtDecoder jwtDecoder() {
		// NimbusJwtDecoder.
		// return NimbusJwtDecoder.withPublicKey(this.key).build();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs",
				"/api/sso/**");
	}

	// @Bean
	// @Override
	// public UserDetailsService userDetailsService() {
	// 	UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();

	// 	return new InMemoryUserDetailsManager(user);
	// }
}

