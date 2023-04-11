package com.example.concur.Repository;

import com.example.concur.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAUserRepo extends JpaRepository<User, String> {
}