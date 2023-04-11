package com.example.cwt.Repository;

import com.example.cwt.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPARefreshTokenRepo extends JpaRepository<RefreshToken, String> {
}
