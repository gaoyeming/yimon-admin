package org.yimon.admin.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.util.constant.GlobalConstants;

import java.util.Date;

/**
 * @author: ym.gao
 * @description: jwt
 * @date: 2024/6/7 17:23
 */
@Slf4j
@Service
public class JwtTokenService {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(GlobalConstants.SECRET);


    /**
     * 生产JWT(token)
     *
     * @param loginName 登录名
     * @return token
     */
    public String generateToken(String loginName) {
        Validate.isNotBank(loginName, "loginName not be null");
        //获取最后登录时间
        Date loginDate = new Date();
        //创建jwt
        return JWT.create()
                .withSubject(loginName)//主题
                .withIssuedAt(loginDate)//签发时间
                .withExpiresAt(new Date(loginDate.getTime() + GlobalConstants.EXPIRE_TIME * 60 * 1000))//过期时间
                .sign(ALGORITHM);
    }

    /**
     * 从token中获取登录名
     *
     * @param token token
     * @return 登录名
     */
    public String getLoginNameFromToken(String token) {
        Validate.isNotBank(token, "token not be empty");
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return true-过期；false-未过期
     */
    public boolean isTokenExpired(String token) {
        Validate.isNotBank(token, "token not be empty");
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            verifier.verify(token);
            return false; // Token is not expired
        } catch (JWTVerificationException e) {
            return true; // Token is expired or invalid
        }
    }

    /**
     * 获取token的签发时间，作为登录时间
     *
     * @param token token
     * @return 签发时间
     */
    public Date getIssuedAt(String token) {
        Validate.isNotBank(token, "token not be empty");
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuedAt();
    }

}
