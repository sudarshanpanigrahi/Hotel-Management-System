package com.hms.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.Key}")
    private String token;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry}")
    private int expiry;

    private Algorithm algo;
    @PostConstruct
    public void init() {
        algo = Algorithm.HMAC256(token);
    }

    public String generateToken(String username) {
        return JWT.create().withClaim("name", username).withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiry)).sign(algo);
    }
    public String getUer(String token){
        DecodedJWT verify = JWT.require(algo).withIssuer(issuer).build().verify(token);
        return  verify.getClaim("name").asString();
    }
}
