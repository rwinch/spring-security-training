package sample.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*.xml")
@WebAppConfiguration
public class MyUserDetailsServiceTest {
	@Autowired
	MyUserDetailsService users;

	@Test(expected = UsernameNotFoundException.class)
	public void loadByUsernameWhenUsernameMissingThenException() {
		users.loadUserByUsername("missing");
	}

	@Test
	public void loadByUsernameWhenAnyUsernameThenFoundWithBCryptPassword() {
		String username = UUID.randomUUID().toString();
		UserDetails user = users.loadUserByUsername(username);

		assertThat(user.getUsername()).isEqualTo(username);
		assertThat(user.getPassword()).isEqualTo(MyUserDetailsService.BCRYPT_PASSWORD);
		assertThat(user.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsOnly("ROLE_USER");
	}
}
