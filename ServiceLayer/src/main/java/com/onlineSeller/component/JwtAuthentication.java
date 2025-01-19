package com.onlineSeller.component;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthentication {

	@Autowired
	JwtUtil jwtUtil;
	
	
	private static final String JWT_AUTHORISATION_HEADER = "Authorization";
	private static final String JWT_AUTHORISATION_HEADER_PREFIX= "Bearer";

	public String authenticate(HttpServletRequest request) {
		if (checkJWTToken(request)) {

			String jwtToken = getToken(request);
			String username = null;

			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);

			} catch (Exception e) {
				return "failed";
			}

			if (StringUtils.isNotEmpty(username)) {
				if (jwtUtil.validateToken(jwtToken, username)) {
					return "success";
				} else {
					return "failed";
				}

			} else {
				return "failed";
			}

		} else {
			return "failed";
		}

	}
	
	public static boolean checkJWTToken(HttpServletRequest request) {
		String authenticationHeader =request.getHeader(JWT_AUTHORISATION_HEADER);
		if(authenticationHeader==null || !authenticationHeader.startsWith(JWT_AUTHORISATION_HEADER_PREFIX))
				return false;
				
		return true;
	}
	
	public static String getToken(HttpServletRequest request)
	{
		
		                         
		return request.getHeader(JWT_AUTHORISATION_HEADER).replace(JWT_AUTHORISATION_HEADER_PREFIX,"");
	}

}
