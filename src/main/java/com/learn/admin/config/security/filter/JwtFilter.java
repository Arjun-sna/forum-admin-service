package com.learn.admin.config.security.filter;

import com.learn.admin.config.security.JwtUtil;
import com.learn.admin.config.security.token.Token;
import com.learn.admin.config.security.token.TokenOperation;
import com.learn.admin.model.AuthUser;
import com.learn.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public abstract class JwtFilter extends OncePerRequestFilter {

//    @Autowired
    private final JwtUtil jwtUtil;

//    @Autowired
    private final UserRepository userRepository;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final Token token = validateAndGetToken(request);
//        if (token == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
////        Token token = jwtUtil.getTokenClaim(jwtToken)
//
//        if (token.getOperation() != TokenOperation.AUTH) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        setSecurityContext(request, token);
//
//        filterChain.doFilter(request, response);
//    }

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
