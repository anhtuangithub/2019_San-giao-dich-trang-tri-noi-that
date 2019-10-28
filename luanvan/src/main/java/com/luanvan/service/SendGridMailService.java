package com.luanvan.service;

public interface SendGridMailService {
	
	void sendHTML(String to, String subject, String body);

}
