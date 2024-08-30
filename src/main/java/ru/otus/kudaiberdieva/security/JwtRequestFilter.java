package ru.otus.kudaiberdieva.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.otus.kudaiberdieva.services.UserService;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private UserService myUserService;

    private JWTUtils jwtUtils;

    @Autowired
    public void setMyUserDetailsService(UserService myUserService) {
        this.myUserService = myUserService;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer")){
            return headerAuth.substring(7);
        }
        logger.info("no header");
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request); //Invokes the Jwt parser and parses the request
            if (jwt != null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt); //gets email address
                UserDetails userDetails = this.myUserService.loadUserByUsername(username); // gets the user's session information
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //checks credentials
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // this request is valid
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // grants access to the resource
            }
        } catch (Exception e){
            logger.info("cannot set user authentication token");
        }
        filterChain.doFilter(request,response); //submit the request and response
    }
}
