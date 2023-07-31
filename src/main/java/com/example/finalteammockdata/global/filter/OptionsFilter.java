package com.example.finalteammockdata.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OptionsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        Enumeration em = request.getHeaderNames();
        while(em.hasMoreElements()) {
            String name = (String)em.nextElement();
            String value = request.getHeader(name);
            System.out.println("name = " + name);
            System.out.println("value = " + value);
        }
        System.out.println("request.getContentType() = " + request.getContentType());

//        for (Part part : request.getParts()) {
//            InputStream inputStream = part.getInputStream();
//            System.out.println("part.getName() = " + part.getName());
//            byte[] rawData = StreamUtils.copyToByteArray(inputStream);
//            String result = new String(rawData);
//            System.out.println("part.result = " + result);
//        }
//        System.out.println("req.getInputStream() = " + req.getInputStream());


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Expose-Headers","*");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}