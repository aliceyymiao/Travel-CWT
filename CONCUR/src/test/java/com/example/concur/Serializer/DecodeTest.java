package com.example.concur.Serializer;

import com.example.concur.Config.SpringUnitTestBase;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DecodeTest {
    @Test
    public void testParseJwt() {
        Decode decoder = new Decode();
        // create a sample JWT
        String email = "ym445@gmail.com";
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("email", email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(8l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();

        // verify that the JWT can be parsed and its claims extracted
        Jws<Claims> claims = decoder.parseJwt(jwtToken);
        assertNotNull(claims.getBody().get("email", String.class));
        assertEquals("ym445@gmail.com", claims.getBody().get("email", String.class));
    }

}

