package com.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Certify;
import com.company.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	void deleteByEmail(String email);

}
