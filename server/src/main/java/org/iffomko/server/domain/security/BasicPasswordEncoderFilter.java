package org.iffomko.server.domain.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class BasicPasswordEncoderFilter implements Filter {

    public static final String LINE_SEPARATOR = "\r\n";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        System.out.println(httpRequest.getRequestURL());
        BufferedReader reader = httpRequest.getReader();
        String content = reader.lines().collect(Collectors.joining(LINE_SEPARATOR));
        chain.doFilter(httpRequest, response);
    }
}
