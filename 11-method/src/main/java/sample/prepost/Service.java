package sample.prepost;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

// FIXME Add @PreAuthorize, @PostAuthorize, & @PostFitler
// Hint use Authz which as bean by named "authz"
public interface Service {

	void denyAll();

	void permitAll();

	void isUser();

	void isAdmin();

	void isAdminOrDba();

	void isValueOdd(String value);

	void isValueEven(String value);

	/**
	 * Should only work if the username passed in is the same as the username of the current user
	 * @param username
	 * @return Greets the username with the username in the result
	 */
	String messageForCurrentUser(String username);

	List<Integer> onlyAllowedEvens(List<Integer> numbers);
}
