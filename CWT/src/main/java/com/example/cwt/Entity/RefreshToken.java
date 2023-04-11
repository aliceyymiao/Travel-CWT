package com.example.cwt.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "refreshtoken")
public class RefreshToken {
    @Id
    @Column(name = "token")
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private TokenInfo tokenInfo;

    @Column(name = "expiration_timestamp")
    private Timestamp expirationTimestamp;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Timestamp getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Timestamp expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }
}
