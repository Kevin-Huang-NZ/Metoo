package me.too.scaffold.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import me.too.scaffold.core.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component(value = "jwtUtil")
public class JwtUtil {
	Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${jwt.secret:a1g2y47dg3dj59fjhhsd7cnewy73j}")
	private String secret;

	// default 8 hours
	@Value("${jwt.expiration:28800}")
	private Long expiration;

	public String generateToken(User loginUser) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", loginUser.getPhone());
		return generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
		    .setIssuedAt(this.generateCurrentDate()).signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}

	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			final Date iat = claims.getIssuedAt();
			final Date exp = claims.getExpiration();
			if (iat.before(lastPasswordReset) || exp.before(generateCurrentDate())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			refreshedToken = this.generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public String verifyToken(String token) {
		String result = "0";
		if (StringUtils.isEmpty(token)) {
			return result;
		}
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			final Date exp = claims.getExpiration();
			if (exp.before(generateCurrentDate())) {
				result = "0";
			} else {
				result = "1";
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return result;
	}

	public String getIdentificationFromToken(String token) {
		String identification = null;
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			identification = claims.getSubject();
		} catch (Exception e) {
			identification = null;
		}
		return identification;
	}
}
