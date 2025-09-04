package com.example.springdemo.security;

import com.example.springdemo.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var requestTokenHeader = request.getHeader("Authorization");
        Claims claim = null;
        String jwtToken;
        String invalid = null;

        /*
         * JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token */
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                claim = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                invalid = "Invalid session";
            } catch (ExpiredJwtException e) {
                invalid = "Session has been expired";
            } catch (MalformedJwtException | SignatureException e) {
                invalid = e.getMessage();
            }
        }

        /*
         * Once we get the token validate it. */
        if (invalid != null) {
            request.setAttribute("invalid", invalid);
        } else {
            if (claim != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Retrieve the authorities as List<?> first
                List<?> authoritiesList = claim.get("authorities", List.class);

                // Then map it to List<SimpleGrantedAuthority>
                List<SimpleGrantedAuthority> authorities = authoritiesList.stream()
                        .map(String.class::cast)
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(claim.getSubject(), request, authorities);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
