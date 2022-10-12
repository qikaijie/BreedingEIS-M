package com.liuxn.yuzhong.app.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liuxn.yuzhong.framework.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    @Autowired
    private JwtConfig jwtConfig;
	
	public static long EXPIRE_1_DAY = 24 * 60 * 60 * 1000;
	public static long EXPIRE_1_WEEK = 7 * 24 * 60 * 60 * 1000;
	public static long EXPIRE_1_MONTH = 30 * 24 * 60 * 60 * 1000;
	public static long EXPIRE_1_YEAR = 365 * 24 * 60 * 60 * 1000;
	/**
	 * 生成jwt token
	 */
	public String generateToken(String userId) {
		Date nowDate = new Date();
		//过期时间
		Date expireDate = new Date(nowDate.getTime() + jwtConfig.getExpire());

		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(userId)//主题，也差不多是个人的一些信息
				.setIssuedAt(nowDate) //创建时间
				.setExpiration(expireDate)//添加Token过期时间
				//.setAudience(audience) //个人签名
				//.setIssuer(issuer) //发送谁
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
				.compact();
	}

	public Claims getClaimByToken(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(jwtConfig.getSecret())
					.parseClaimsJws(token)
					.getBody();
		}catch (Exception e){
			logger.debug("token验证错误,请重新登陆 ", e);
			return null;
		}
	}

	/**
	 * token是否过期
	 * @return  true：过期
	 */
	public boolean isTokenExpired(Date expiration) {
		return expiration.before(new Date());
	}
}
