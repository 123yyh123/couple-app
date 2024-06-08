package cn.yahaoyang.utils;

import cn.yahaoyang.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yyh
 * @date 2024/6/7
 */

@Configuration
public class JWTUtil {

    private static JwtProperties jwtProperties;

    @Autowired
    private void setJwtProperties(JwtProperties jwtProperties) {
        JWTUtil.jwtProperties = jwtProperties;
    }
    public static String createToken(Integer userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        claims.put("currentTimeMillis",System.currentTimeMillis());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,jwtProperties.getSecret()).compact();
    }

    public static Map<String,Object> parseToken(String token){
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    public static Integer getCurrentUserId(String token){
        return (Integer) parseToken(token).get("userId");
    }
}