package com.example.demo.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public enum ApplicationUserRole {
	
	STUDENT(Set.of()),
	TRAINEE(Set.of()),
	ADMIN(Set.of(ApplicationUserPermission.COURSE_READ,ApplicationUserPermission.STUDENT_READ));	
	
	private final Set<ApplicationUserPermission> permissions;
	
	 ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}
	 
	 public Set<ApplicationUserPermission> getPermission() {
		return permissions;
	}
	 
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> permissions = getPermission().stream().map(permission->new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
		
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}

}
