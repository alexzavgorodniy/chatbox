package com.websoc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response;
        if (res instanceof HttpServletResponse && req instanceof HttpServletRequest) {
            response = (HttpServletResponse) res;
        } else {
            throw new IllegalArgumentException("Passed malformed data to auth flow filter");
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Expose-Headers", "x-token");
        response.setHeader("Access-Control-Allow-Headers", " Authorization, x-token, Content-Type, Accept, Origin, Access-Control-Expose-Headers");
        response.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(req, res);
    }

}