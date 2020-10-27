package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.demo.auth.ApplicationUserService;
import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug=true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService ;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService= applicationUserService ;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		//JST
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
		.addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class) 
		.authorizeRequests()
		.antMatchers("/","index").permitAll()
//		.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name() )
//		.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_READ.getPermission())
//		.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_READ.getPermission())
//		.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_READ.getPermission())
//		.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicationUserRole.STUDENT.name())
		.anyRequest().authenticated()
		
// FORM-BASED auth
		//		.and()
//		.formLogin()
//		.loginPage("/login").permitAll()
//		.defaultSuccessUrl("/courses").and().rememberMe().and().
//		logout().logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID","remember-me").logoutSuccessUrl("/login")
;
	}

	// Original solution with inMemoryDatabase (if dao doesn't work)
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails user1 = User.builder().username("student").password(passwordEncoder.encode("password"))
//				//.roles(ApplicationUserRole.STUDENT.name())
//				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
//				.build();
//		UserDetails user2 = User.builder().username("admin").password(passwordEncoder.encode("password"))
//				//.roles(ApplicationUserRole.ADMIN.name())
//				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
//				.build();
//		UserDetails user3 = User.builder().username("trainee").password(passwordEncoder.encode("password"))
//				//.roles(ApplicationUserRole.TRAINEE.name())
//				.authorities(ApplicationUserRole.TRAINEE.getGrantedAuthorities())
//				.build();
//
//		
//		
//		return new InMemoryUserDetailsManager(user1,user2,user3);
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider() ;
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(applicationUserService);
		
		return daoAuthenticationProvider;
	}
}
