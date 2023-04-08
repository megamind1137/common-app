package com.ms.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String email);

	void deleteByEmail(String email);

	Optional<User> findByUsernameAndEnabledTrue(String username);
}