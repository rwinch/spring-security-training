package sample.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import sample.data.MyUser;
import sample.data.MyUserRepository;

@Component
public class MyUserDetailsService implements UserDetailsService {
	MyUserRepository myUserRepository;

	public MyUserDetailsService(MyUserRepository myUserRepository) {
		super();
		this.myUserRepository = myUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser myUser = myUserRepository.findByUsername(username);
		// FIXME leverage MyUser to return an object that is both MyUser and UserDetails
		return null;
	}
}
