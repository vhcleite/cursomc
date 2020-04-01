package com.vleite.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String BEARER = "Bearer ";

	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = request.getHeader("Authorization");
		if(token != null) {
			UsernamePasswordAuthenticationToken auth = getAuthenticationToken(token);
			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);   
			}
			
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		if(token.startsWith(BEARER)) {
			token = token.substring(BEARER.length());
			if (jwtUtil.isTokenValid(token)) {
				String username = jwtUtil.getUserNameFromToken(token);
				UserDetails user = userDetailsService.loadUserByUsername(username);
				return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			}
		}
		return null;
	}

}
