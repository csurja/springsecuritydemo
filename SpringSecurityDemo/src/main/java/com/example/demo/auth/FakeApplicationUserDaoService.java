package com.example.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{
	
	private final PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers().stream().filter(
				applicationUser -> applicationUser.getUsername().equals(username)).
				findFirst() ;
	}
	
	private List<ApplicationUser> getApplicationUsers(){
		List<ApplicationUser>  applicationUsers =  Lists.newArrayList(
				new ApplicationUser(ApplicationUserRole.ADMIN.getGrantedAuthorities(),
						passwordEncoder.encode("password"), 
						"admin", 
						true, 
						true, 
						true)) ;
		
		return applicationUsers;
	}

}
