package sample.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AuthzTests {

	Authz authz = new Authz();

	@Test
	public void isOddWhenNullThenFalse() {
		assertThat(authz.isOdd(null)).isFalse();
	}

	@Test
	public void isOddWhenEmptuyThenFalse() {
		assertThat(authz.isOdd("")).isFalse();
	}

	@Test
	public void isOddWhenOddThenFalse() {
		assertThat(authz.isOdd("a")).isTrue();
	}

	@Test
	public void isOddWhenEvenThenFalse() {
		assertThat(authz.isOdd("ab")).isFalse();
	}
}
