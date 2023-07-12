package com.company.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByEmail(String email);
	
 }
