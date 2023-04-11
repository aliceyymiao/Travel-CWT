package com.example.cwt.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "token")
public class TokenInfo {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration_timestamp")
    private Timestamp expirationTimestamp;

    @JsonIgnore
    @OneToOne(mappedBy = "tokenInfo")
    private RefreshToken refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Timestamp expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
}
