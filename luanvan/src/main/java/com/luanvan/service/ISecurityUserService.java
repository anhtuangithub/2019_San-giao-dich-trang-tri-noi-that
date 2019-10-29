package com.luanvan.service;

public interface ISecurityUserService {
	 String validatePasswordResetToken(String email, String token);
}
