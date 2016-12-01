package sample.security;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import sample.data.MyUser;

// FIXME make MyUserDetails both a UserDetails and a MyUser object
public class MyUserDetails {
	Collection<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

	public MyUserDetails(MyUser myUser) {
		BeanUtils.copyProperties(myUser, this);
	}

}
