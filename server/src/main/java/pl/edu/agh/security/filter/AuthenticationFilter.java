package pl.edu.agh.security.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import pl.edu.agh.security.service.TokenAuthenticationService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {
    private TokenAuthenticationService tokenAuth;

    public AuthenticationFilter(TokenAuthenticationService tokenAuth) {
        this.tokenAuth = tokenAuth;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(tokenAuth.getAuthentication((HttpServletRequest) request));
        chain.doFilter(request, response);
    }
}
