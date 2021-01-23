package com.expenses.core.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends BasicAuthenticationFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public TokenFilter(AuthenticationManager authenticationManager, TokenService tokenService, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (tokenService.isToken(authHeader)) {
            final String token = tokenService.extractToken(authHeader);
            if (tokenService.isValidToken(token)) {
                final String username = tokenService.getUsernameFromToken(token);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final Authentication auth = new UsernamePasswordAuthenticationToken(
                        username, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                refreshToken(response, token);
            }
        }
        chain.doFilter(request, response);
    }

    private void refreshToken(HttpServletResponse response, String token) {
        final String refreshedToken = tokenService.refreshToken(token);
        response.addHeader("Access-Control-Expose-Headers", AUTHORIZATION_HEADER);
        response.addHeader(AUTHORIZATION_HEADER, tokenService.prepareTokenHeader(refreshedToken));
    }
}
