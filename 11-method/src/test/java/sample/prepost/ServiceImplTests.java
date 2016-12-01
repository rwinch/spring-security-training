package sample.prepost;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sample.security.Authz;
import sample.security.WithAdmin;
import sample.security.WithDba;
import sample.security.WithUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ServiceImplTests {
	@Autowired
	Service service;

	@WithAdmin
	@Test(expected = AccessDeniedException.class)
	public void denyAllWhenAdminThenDenied() {
		service.denyAll();
	}

	@WithUser
	@Test
	public void permitAllWhenUserThenAllow() {
		service.permitAll();
	}

	@Test
	@WithUser
	public void isUserWhenUserThenAllowed() {
		service.isUser();
	}

	@WithUser
	@Test(expected = AccessDeniedException.class)
	public void isAdminWhenUserThenDenied() {
		service.isAdmin();
	}

	@WithAdmin
	@Test
	public void isAdminWhenAdminThenAllowed() {
		service.isAdmin();
	}

	@WithAdmin
	@Test
	public void isAdminOrDbaWhenAdminThenAllowed() {
		service.isAdminOrDba();
	}

	@WithDba
	@Test
	public void isAdminOrDbaWhenDbaThenAllowed() {
		service.isAdminOrDba();
	}

	@WithUser
	@Test(expected = AccessDeniedException.class)
	public void isAdminOrDbaWhenUserThenDenied() {
		service.isAdminOrDba();
	}

	@WithUser
	@Test
	public void isValueOddWhenOddThenAllowed() {
		service.isValueOdd("a");
	}

	@WithUser
	@Test(expected = AccessDeniedException.class)
	public void isValueOddWhenEvenThenDenied() {
		service.isValueOdd("ab");
	}

	@WithUser
	@Test(expected = AccessDeniedException.class)
	public void isValueEvenWhenOddThenDenied() {
		service.isValueEven("a");
	}

	@WithUser
	@Test
	public void isValueEvenWhenEvenThenAllowed() {
		service.isValueEven("ab");
	}

	@WithUser
	@Test
	public void messageForCurrentUserWhenWithUserAndUserThenAllowed() {
		service.messageForCurrentUser("user");
	}

	@WithAdmin
	@Test(expected = AccessDeniedException.class)
	public void messageForCurrentUserWhenWithAdminAndUserThenDenied() {
		service.messageForCurrentUser("user");
	}

	@WithUser
	@Test(expected = AccessDeniedException.class)
	public void messageForCurrentUserWhenWithUserAndAdminThenDenied() {
		service.messageForCurrentUser("admin");
	}

	@WithAdmin
	@Test
	public void messageForCurrentUserWhenWithAdminAndAdminThenAllowed() {
		service.messageForCurrentUser("admin");
	}

	@WithUser
	@Test
	public void onlyAllowedEvensWhenEvensAndOddsThenEvens() {
		List<Integer> evensOnly = service.onlyAllowedEvens(Arrays.asList(8,7,2,3,1,9));
		assertThat(evensOnly).containsOnly(8,2);
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class Config {
		@Bean
		Authz authz() {
			return new Authz();
		}

		@Bean
		ServiceImpl service() {
			return new ServiceImpl();
		}
	}
}
