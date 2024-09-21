package com.jc.nrtcloud.util;

import com.jc.nrtcloud.sevice.JwtTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && JwtTokenUtil.validateToken(token)) {
            String username = JwtTokenUtil.getUsernameFromToken(token);
            // 在这里可以将用户信息存储在 SecurityContext 或其他地方
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未授权");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
