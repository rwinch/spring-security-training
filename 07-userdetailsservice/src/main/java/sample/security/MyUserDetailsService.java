package sample.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {
	/**
	 * BCrypt password
	 */
	public static final String BCRYPT_PASSWORD = "$2a$04$Xy6kb5vy9ei/a2NiunJwkuVj2M3.YPthIiVzXynpyHLiBgIWFgefK";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if("missing".equals(username)) {
			throw new UsernameNotFoundException("Not Found");
		}
		return new User(username, BCRYPT_PASSWORD, AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

}
