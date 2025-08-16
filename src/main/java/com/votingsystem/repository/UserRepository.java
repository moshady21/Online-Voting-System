package com.votingsystem.repository;

import com.votingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // Remove findByUsername if you had it, or keep both if needed
}