package com.example.finalteammockdata.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OptionsFilter implements Filter{




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


        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods","*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            try {
                chain.doFilter(req, res);
            } catch (RuntimeException e) {
                log.info(e.getCause().getClass().getName());
            }
        }
    }

    @Override
    public void destroy() {

    }
}