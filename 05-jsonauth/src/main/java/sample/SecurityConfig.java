package sample;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public HttpStatusEntryPoint entryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling()
				.authenticationEntryPoint(entryPoint())
				.and()
			.authorizeRequests()
				.mvcMatchers("/login").permitAll()
				.anyRequest().authenticated();
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
