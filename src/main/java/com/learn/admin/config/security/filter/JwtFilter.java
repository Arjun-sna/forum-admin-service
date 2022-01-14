package com.learn.admin.config.security.filter;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.config.security.token.Token;
import com.learn.admin.model.AuthUser;
import com.learn.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletRequest;

public abstract class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    protected void setSecurityContext(HttpServletRequest request, Token token) {
        UsernamePasswordAuthenticationToken authenticationToken = userRepository
                .findByEmail(token.getEmail())
                .map(AuthUser::new)
                .map(authUser -> {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    return authentication;
                })
                .orElse(null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    protected Token validateAndGetToken(HttpServletRequest request) {
        final String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasLength(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
            return null;
        }

        final String jwtToken = tokenHeader.split(" ")[1].trim();
        if (!jwtUtil.validate(jwtToken)) {
            return null;
        }

        return jwtUtil.getTokenClaim(jwtToken);
    }
}
