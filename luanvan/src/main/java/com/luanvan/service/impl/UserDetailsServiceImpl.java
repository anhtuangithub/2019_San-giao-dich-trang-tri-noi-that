package com.luanvan.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luanvan.model.CustomUserDetails;
import com.luanvan.model.Role;
import com.luanvan.model.Users;
import com.luanvan.repo.UsersRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private UsersRepository userRepository;
	
	
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new CustomUserDetails(user,grantedAuthorities,getStoreId(user),getCustomerId(user));
    }
    private Long getStoreId(Users user) {
		if(user.getStore() != null) return user.getStore().getId();
		return null;
	}
	
	private Long getCustomerId(Users user) {
		if(user.getCustomer() != null) return user.getCustomer().getId();
		return null;
	}

}