package sample.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// FIXME expose this as a Bean
public class MyUserDetailsService implements UserDetailsService {
	/**
	 * BCrypt password
	 */
	public static final String BCRYPT_PASSWORD = "$2a$04$Xy6kb5vy9ei/a2NiunJwkuVj2M3.YPthIiVzXynpyHLiBgIWFgefK";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// FIXME implement method
		if("missing".equals(username)) {

		}
		return null;
	}

}
