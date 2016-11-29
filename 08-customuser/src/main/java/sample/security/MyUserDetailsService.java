package sample.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sample.data.MyUser;
import sample.data.MyUserRepository;

public class MyUserDetailsService implements UserDetailsService {
	MyUserRepository myUserRepository;

	public MyUserDetailsService(MyUserRepository myUserRepository) {
		super();
		this.myUserRepository = myUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser myUser = myUserRepository.findByUsername(username);
		if(myUser == null) {
			throw new UsernameNotFoundException("Oops");
		}
		return new MyUserDetails(myUser);
	}
}
