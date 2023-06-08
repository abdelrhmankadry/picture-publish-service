package com.kadry.picturePublishingService.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthJwtTokenFilter extends OncePerRequestFilter {

    private final Jwt jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = resolveToken(header);

        if(token!= null && jwtUtil.validate(token)){
            SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(String header) {
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.split(" ")[1];
        }
        return null;
    }
}
