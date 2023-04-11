package com.example.customersystem.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String email = servletRequest.getParameter("email");
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            System.err.println("Incorrect email format");

            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpServletResponse.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            PrintWriter out = httpServletResponse.getWriter();
            out.print(mapper.writeValueAsString("Incorrect email format!"));
            out.flush();
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

}
