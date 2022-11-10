package org.interbanking.com.security;

import org.interbanking.com.entities.User;
import org.interbanking.com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findOneByEmail(email)
					   .orElseThrow(()-> new UsernameNotFoundException("The user with email " + email + " does not exists"));
		
		
		return new UserDetailsImpl(user);
	}

}
