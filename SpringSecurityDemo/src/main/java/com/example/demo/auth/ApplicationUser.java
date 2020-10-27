package com.example.demo.auth;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUser implements UserDetails {

	private final Set<? extends GrantedAuthority> grantedAuthorities ;
	private final String password;
	private final String usernam;
	private final boolean isAccountNonExpired;
	private final boolean isAccountNonLocked ;
	private final boolean isEnabled;

	
	public ApplicationUser(Set<? extends GrantedAuthority> grantedAuthorities, String password, String usernam,
			boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isEnabled) {
		this.grantedAuthorities = grantedAuthorities;
		this.password = password;
		this.usernam = usernam;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isEnabled = isEnabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return usernam;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
