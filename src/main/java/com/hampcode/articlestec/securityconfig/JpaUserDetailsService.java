package com.hampcode.articlestec.securityconfig;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hampcode.articlestec.model.Account;
import com.hampcode.articlestec.model.Role;
import com.hampcode.articlestec.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly =true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Account
		Account user=userRepository.findByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> authorities=new ArrayList<>();
		
		//Roles
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		//User==>Spring Security
		return new User(user.getUserName(), user.getPassword(), user.getEnabled(),
				true,true, true,authorities);
	}

}
