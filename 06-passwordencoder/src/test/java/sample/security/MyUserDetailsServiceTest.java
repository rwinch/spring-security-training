package sample.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*.xml")
@WebAppConfiguration
public class MyUserDetailsServiceTest {
	@Autowired
	UserDetailsService users;

	@Test(expected = UsernameNotFoundException.class)
	public void loadByUsernameWhenUsernameMissingThenException() {
		users.loadUserByUsername("missing");
	}

	@Test
	public void loadByUsernameWhenFoundThenPasswordBCrypt() {
		String username = "user";
		UserDetails user = users.loadUserByUsername(username);

		assertThat(user.getUsername()).isEqualTo(username);
		assertThat(user.getPassword()).isEqualTo("$2a$04$Xy6kb5vy9ei/a2NiunJwkuVj2M3.YPthIiVzXynpyHLiBgIWFgefK");
		assertThat(user.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsOnly("ROLE_USER");
	}
}
