package sample.prepost;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
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

	/**
	 * Should only work if the username passed in is the same as the username of the current user
	 * @param username
	 * @return Greets the username with the username in the result
	 */
	@PostAuthorize("returnObject?.contains(authentication?.name)")
	String messageForCurrentUser(String username);

	@PostFilter("filterObject % 2 == 0")
	List<Integer> onlyAllowedEvens(List<Integer> numbers);
}
