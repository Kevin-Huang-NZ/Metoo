package me.too.scaffold.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {

  @Value("${jwt.tokenheader:x-auth-token}")
  private String tokenHeader;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	  List<String> headers = Arrays.asList(tokenHeader, "Accept", "Access-Control-Request-Method", 
			  "Access-Control-Request-Headers", "Accept-Language", "Authorization", 
			  "Content-Type", "Request-Name", "Request-Surname", 
			  "Origin");
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers", String.join(",", headers));
      response.addHeader("Access-Control-Expose-Headers", tokenHeader);
      if ("OPTIONS".equals(request.getMethod())) {
          response.setStatus(HttpServletResponse.SC_OK);
      } else { 
          filterChain.doFilter(request, response);
      }
  }
}