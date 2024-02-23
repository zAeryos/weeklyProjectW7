package it.epicode.weeklyProjectW7.security;

import it.epicode.weeklyProjectW7.exception.UnAuthorizedException;
import it.epicode.weeklyProjectW7.model.User;
import it.epicode.weeklyProjectW7.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization==null||!authorization.startsWith("Bearer ")){
            throw new UnAuthorizedException("Token not present");
        }

        String token = authorization.substring(7);

        jwtTools.validateToken(token);

        String  username    = jwtTools.extractUsernameFromToken(token);
        User    user        = userService.getUserByUsername(username);

        checkPathVariable(request, user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

    private void checkPathVariable(HttpServletRequest request, User utente){
        String[] parts = request.getServletPath().split("/");
        System.out.println(parts.length);
        Arrays.stream(parts).forEach(System.out::println);
        if(parts.length==3) {
            if (parts[1].equals("autori")) {
                int id = Integer.parseInt(parts[2]);

                if(utente.getId()!=id){
                    throw new UnAuthorizedException("Non sei abilitato ad utilizzare il servizio per id differenti dal tuo");
                }

            } else if (parts[1].equals("utenti")) {
                if(!utente.getUsername().equals(parts[2])){
                    throw new UnAuthorizedException("Non sei abilitato ad utilizzare il servizio per un username differente dal tuo");
                }
            }
        }
    }
}
