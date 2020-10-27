package com.example.demo.misc;

import java.util.Optional;

class User{
	private final String username = "John";
	private final String email = "john@gmx.de";
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
}

public class DummyTest {


	
	public static void main(String[] args) {
		User user = new User();
		String email = Optional.ofNullable(user)
				.map(u -> u.getEmail()).orElse("test") ;
		
		System.out.println(email);
		
		
	}
	
}
