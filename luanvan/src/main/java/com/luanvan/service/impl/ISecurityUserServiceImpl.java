package com.luanvan.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luanvan.model.PasswordResetToken;
import com.luanvan.repo.PasswordResetTokenRepository;
import com.luanvan.service.ISecurityUserService;

@Service
public class ISecurityUserServiceImpl implements ISecurityUserService  {

	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;
	 
	@Override
	public String validatePasswordResetToken(String email, String token) {
		final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (!passToken.getUser().getEmail().equalsIgnoreCase(email))) {
            return "invalidToken";
        }
        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	return "expired";
        }
//        final Users user = passToken.getUser();
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user, new SimpleGrantedAuthority("ROLE_ADMIN"));
//
//        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
	}

}
