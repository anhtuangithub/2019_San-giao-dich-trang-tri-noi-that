package com.luanvan.dto.request;

import java.util.Set;

import com.luanvan.model.Role;

public class TestDTO {

	private String email;
	private String password;
	
	private Set<Role> roleList;
	
	public void  setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Role> getRoleList(){
		return roleList;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
}
