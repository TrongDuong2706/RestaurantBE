package com.cyphersoft.osahaneat.security;

import com.cyphersoft.osahaneat.service.TokenBlacklistService;
import com.cyphersoft.osahaneat.utils.JwtUtilsHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtilsHelper jwtUtilsHelper;
    @Autowired
    TokenBlacklistService tokenBlacklistService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        if(token != null && !tokenBlacklistService.isTokenBlacklisted(token)){
            if( jwtUtilsHelper.verifyToken(token)){
                String username = jwtUtilsHelper.extractUsername(token);
                String role = jwtUtilsHelper.extractRole(token);

                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + role);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);
    }

    private String getTokenFromHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token =null;
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            token = header.substring(7);
        }
        return token;
    }
}
