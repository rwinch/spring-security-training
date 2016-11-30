package sample.prepost;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
