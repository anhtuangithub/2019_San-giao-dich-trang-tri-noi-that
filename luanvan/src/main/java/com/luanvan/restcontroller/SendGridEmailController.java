package com.luanvan.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.service.SendGridMailService;

@RestController
public class SendGridEmailController {

	private SendGridMailService sendGridMailService;
	
	@Autowired
	public SendGridEmailController(SendGridMailService sendGridMailService){
		this.sendGridMailService = sendGridMailService;
	}
	
	@GetMapping("test-send-mail")
	public void testmail() {
		sendGridMailService.sendHTML("leanhtuan9889@gmail.com", "Hello", "<p><b>Hello</b> Nguuyen</p>");
	}
}
