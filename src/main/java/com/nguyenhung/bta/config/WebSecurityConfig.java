package com.nguyenhung.bta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests()
    		.antMatchers("/css/**","/img/**", "/js/**"
    			,"/scss/**", "/vendor/**", "/logout", "/register").permitAll()
    		.antMatchers("/admin/**", "/admin")
    			.hasAnyRole("ADMIN", "MODERATOR")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login").permitAll()
            .usernameParameter("account")
            .passwordParameter("password")
            .defaultSuccessUrl("/default", true)
            .failureUrl("/login?error")
            .and()
            .csrf().disable()
        .exceptionHandling()
            .accessDeniedPage("/error")
            ;
	}
}
