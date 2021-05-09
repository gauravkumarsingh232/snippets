package com.xiffox.snippets.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTDemo {
    String clientSecret;
    String clientKey;
    String audience;

    public JWTDemo(String clientSecret, String clientKey, String audience) {
        this.clientSecret = clientSecret;
        this.clientKey = clientKey;
        this.audience = audience;
    }

    public static void main(String[] args) {
        JWTDemo demo = new JWTDemo("clientSecret", "clientKey", "audience");

        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        Date now = new Date();
        Date issuedAt = new Date(now.getTime() - 6*3600*1000);
        Date expiresAt = new Date(now.getTime() + 6*3600*1000);
        String jwtId = UUID.randomUUID().toString();

        try {
            Algorithm algorithm = Algorithm.HMAC256(demo.clientSecret);

            String jwtToken = JWT.create()
                    .withHeader(headerClaims)
                    .withSubject(demo.clientKey)
                    .withAudience(demo.audience)
                    .withIssuer(demo.clientKey)
                    .withExpiresAt(expiresAt)
                    .withIssuedAt(issuedAt)
                    .withJWTId(jwtId)
                    .sign(algorithm);

            System.out.println(jwtToken);

        } catch (JWTCreationException ex){
            //Invalid Signing configuration / Couldn't convert Claims.
            System.out.println("Could Not Generate token - Exception : "+ ex);
        }
    }
}
