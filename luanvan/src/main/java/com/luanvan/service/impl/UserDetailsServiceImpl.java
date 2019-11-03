package com.luanvan.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
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
import com.luanvan.model.Member;
import com.luanvan.model.Role;
import com.luanvan.model.Users;
import com.luanvan.repo.MemberRepository;
import com.luanvan.repo.UsersRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private UsersRepository userRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Role> roles = user.getRoles();
        Date hethan = new Date();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        if(grantedAuthorities.contains(new SimpleGrantedAuthority("ROLE_STORE"))) {
        	Optional<Member> member = memberRepository.memberStore(user.getStore().getId());
        	if(!member.isPresent()) {
        		grantedAuthorities.remove(new SimpleGrantedAuthority("ROLE_STORE"));
        		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_HETHAN"));
        	}
        	else {
        		hethan = member.get().getEnd_time();
        	}
        }
        
        return new CustomUserDetails(user,grantedAuthorities,getStoreId(user),getCustomerId(user),hethan);
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
