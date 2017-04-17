package pl.edu.agh.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.edu.agh.model.User;
import pl.edu.agh.security.UserAuthentication;
import pl.edu.agh.security.service.TokenAuthenticationService;
import pl.edu.agh.security.service.UserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private TokenAuthenticationService tokenAuthService;
    private UserDetailsService userDetailsService;

    public LoginFilter(String urlMapping, TokenAuthenticationService tokenAuthService,
                          UserDetailsService userDetailsService, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(urlMapping));
        this.tokenAuthService = tokenAuthService;
        this.userDetailsService = userDetailsService;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(request.getParameter("username"),
                                                                                                 request.getParameter("password"));
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = userDetailsService.loadUserByUsername(authResult.getName());
        UserAuthentication userAuth = new UserAuthentication(user);

        tokenAuthService.addAuthentication(response, userAuth);
        SecurityContextHolder.getContext().setAuthentication(userAuth);
    }
}
