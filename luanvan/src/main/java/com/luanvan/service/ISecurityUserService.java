package com.luanvan.service;

public interface ISecurityUserService {
	 String validatePasswordResetToken(long id, String token);
}
