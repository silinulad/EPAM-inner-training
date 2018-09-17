package com.example.silin.guitarshop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.anyRequest().authenticated()
		.and()
			.formLogin()
				.usernameParameter("user")
				.passwordParameter("password") 
				.loginPage("/login")
				.permitAll()
		.and()
			.logout()
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll()
		.and()
			.rememberMe()
				.tokenValiditySeconds(60);
	}
	
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring()
	                .antMatchers("/css/**", "/js/**","/images/**","/fonts/**");
	    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

}
