package com.maculuve.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		if (StringUtils.isNotBlank(token) && jwtTokenProvider.validateToken(token)) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);

	}

}
