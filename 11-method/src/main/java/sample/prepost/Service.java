package sample.prepost;

import org.springframework.security.access.prepost.PreAuthorize;

public interface Service {

	@PreAuthorize("denyAll")
	void denyAll();

	@PreAuthorize("permitAll")
	void permitAll();

	@PreAuthorize("hasRole('USER')")
	void isUser();

	@PreAuthorize("hasRole('ADMIN')")
	void isAdmin();

	@PreAuthorize("hasAnyRole('ADMIN','DBA')")
	void isAdminOrDba();

	@PreAuthorize("@authz.isOdd(#value)")
	void isValueOdd(String value);

	@PreAuthorize("!@authz.isOdd(#value)")
	void isValueEven(String value);
}
