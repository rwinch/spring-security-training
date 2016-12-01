package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.mvcMatchers("/secret").hasRole("ADMIN")
				.mvcMatchers("/admin/**").hasRole("ADMIN")
				.mvcMatchers("/users/{username}/emails").access("authentication?.name == #username")
				.mvcMatchers("/users/{username}/phones").access("@authz.isOdd(#username)")
				.mvcMatchers("/public/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.httpBasic();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User
			.withUsername("user")
			.password("password")
			.roles("USER")
			.build();
		InMemoryUserDetailsManager result = new InMemoryUserDetailsManager();
		result.createUser(user);
		return result;
	}
}
