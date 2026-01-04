package com.project2026.url_shortener.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
@Component
public class ClientIpResolver {
	public String resolve(HttpServletRequest request) {
		String xf = request.getHeader("X-Forwarded-For");
		if(xf!=null && !xf.isBlank()) {	
			return xf.split(",")[0].trim();
		}
		return request.getRemoteAddr();
	}
}
