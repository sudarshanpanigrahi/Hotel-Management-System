package com.hms.Config;

import com.hms.Entity.AppUser;
import com.hms.Repository.AppUserRepository;
import com.hms.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService js;
    private AppUserRepository ar;

    public JWTFilter(JWTService js, AppUserRepository ar) {
        this.js = js;
        this.ar = ar;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            String token = header.substring(8, header.length() - 1);
            String username = js.getUer(token);
            Optional<AppUser> user = ar.findByUsername(username);
            if (user.isPresent()) {
                AppUser au = user.get();
                UsernamePasswordAuthenticationToken userTK = new UsernamePasswordAuthenticationToken(au,null,
                        Collections.singleton(new SimpleGrantedAuthority(au.getRole())));
                userTK.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userTK);
            }
        }
        filterChain.doFilter(request, response);

    }
}
