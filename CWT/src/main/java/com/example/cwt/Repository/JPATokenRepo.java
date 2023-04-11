package com.example.cwt.Repository;

import com.example.cwt.Entity.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPATokenRepo extends JpaRepository<TokenInfo, String> {
}
