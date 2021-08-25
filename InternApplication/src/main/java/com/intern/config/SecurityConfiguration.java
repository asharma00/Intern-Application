package com.intern.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import	com.intern.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private UserService userService;
	
	@Autowired
	public SecurityConfiguration(AuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//	http.antMatcher("/index");
		//admin can make some
		//markAcceptedAttendance
		http
		
	 .headers()
	   .frameOptions().disable()
	      .and()
		
		
		
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/toregister/**").permitAll()
		.antMatchers("/registration**", "/js/**","/css/**","/img/**","/images/**","/upload/**","/uploads/**","/index/**","/emailForApplicationStatus/**","/otpForApplicationStatus**","/validateOtp**","/fetchApplicationStatus**", "/api/v3/checkemail", "/downloadFiles/**", "/uploadEdit", "/api/v2/getCerti").permitAll()
		 //Shortcut for specifying URLs require a particular role. If you do not want tohave "ROLE_" automatically inserted see hasAuthority(String).
		//Parameters:role the role to require (i.e. USER, ADMIN, etc). Note, it should notstart with "ROLE_" as this is automatically inserted.
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/instruction/**").hasRole("STUDENT")
		.antMatchers("/api/v3/**").hasAnyRole("STUDENT","USER","ADMIN","MENTOR")
		.antMatchers("/admin/**", "flags/fetchInFlag").hasRole("ADMIN")
		.antMatchers("/api/v4/**").permitAll()
		.antMatchers("/mentorPage/**").hasRole("MENTOR")   //for mentor-activities
		.anyRequest().authenticated()
		.and()
	      .exceptionHandling().accessDeniedPage("/login").
	      and().portMapper()               
	        .http(8080).mapsTo(8443).
		and()
		.formLogin().loginPage("/login")
		.successHandler(authenticationSuccessHandler)
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.permitAll();
		
		PortMapperImpl portMapper = new PortMapperImpl();
		portMapper.setPortMappings(Collections.singletonMap("80","443"));
		PortResolverImpl portResolver = new PortResolverImpl();
		portResolver.setPortMapper(portMapper);
		LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint(
				"/login");
		entryPoint.setPortMapper(portMapper);
		entryPoint.setPortResolver(portResolver);
		http
			.exceptionHandling()
				.authenticationEntryPoint(entryPoint);
	}

}
