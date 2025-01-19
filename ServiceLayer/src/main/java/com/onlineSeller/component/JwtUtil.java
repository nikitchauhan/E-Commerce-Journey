package com.onlineSeller.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.onlineSeller.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
  
public class JwtUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String ISSUER = "Admin_Corporation";

	@Value("${jwt.expiry}")
	public long JWT_TOKEN_VALIDITY;

	@Value("${jwt.secret}")
	public String secret;

	
	@Autowired
	UserRepository userRepo;

	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token, Claims::getSubject);

	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

	}

	@SuppressWarnings("unchecked")
	public Collection<Map<String, String>> getAuthorityFromToken(String token) {
		
		Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return (Collection<Map<String, String>>) claims.get("authority");

	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());

	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());

	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, username);

	}

	public String generateToken(Collection<? extends GrantedAuthority> list, String subject) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("authority", list);

		return doGenerateToken(claims, subject);

	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		try {

			return Jwts.builder().setClaims(claims).setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
					.signWith(SignatureAlgorithm.HS512, secret).compact();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Boolean validateToken(String token, String userDetails) {

		final String username = getUsernameFromToken(token);

		String isUserActive = userRepo.getIsActive(username.split(",")[0]);
		


		return (username.equals(userDetails) && !isTokenExpired(token) && (isUserActive.equalsIgnoreCase("true")));

	}
}
