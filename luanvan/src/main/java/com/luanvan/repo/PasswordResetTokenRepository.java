package com.luanvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luanvan.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);
}
