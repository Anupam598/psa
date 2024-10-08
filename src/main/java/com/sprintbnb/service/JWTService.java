package com.sprintbnb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sprintbnb.entity.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private  String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

  private Algorithm algorithm;

  private static  final String USER_NAME = "username";

  @PostConstruct
   public void postConstruct(){
      algorithm = Algorithm.HMAC256(algorithmKey);

  }
  // Short form to learn COMPUTER Engineer IS UNEMPLOYEE
  public String generateToken(AppUser user){
   return   JWT.create()
              .withClaim(USER_NAME , user.getUsername())
              .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
              .withIssuer(issuer)
              .sign(algorithm);
  }


  public String getUsername(String token){
      DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
      return  decodedJWT.getClaim(USER_NAME).asString();

  }



}

// .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))