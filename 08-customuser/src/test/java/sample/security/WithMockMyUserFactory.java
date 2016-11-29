package sample.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import sample.data.MyUser;

public class WithMockMyUserFactory implements WithSecurityContextFactory<WithMockMyUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockMyUser withUser) {
		MyUser user = myUser(withUser.value());

		List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList("ROLE_USER");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
				user.getPassword(), roles);

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}

	public static MyUser myUser(String username) {
		MyUser user = new MyUser();
		user.setFirstName("First-" + username);
		user.setLastName("Last-" + username);
		user.setUsername(username);
		user.setPassword("notused");
		return user;
	}
}
