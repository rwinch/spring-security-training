package sample.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*.xml")
@WebAppConfiguration
public class MyUserRepositoryTest {
	@Autowired
	MyUserRepository users;

	@Test
	public void robNotNull() {
		String username = "user";
		MyUser rob = users.findByUsername(username);
		assertThat(rob).isNotNull();
		assertThat(rob.getFirstName()).isEqualTo("Rob");
		assertThat(rob.getLastName()).isEqualTo("Winch");
		assertThat(rob.getUsername()).isEqualTo(username);
		assertThat(rob.getPassword()).isEqualTo("$2a$04$Xy6kb5vy9ei/a2NiunJwkuVj2M3.YPthIiVzXynpyHLiBgIWFgefK");
	}
}
