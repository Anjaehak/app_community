package com.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Certify;

public interface CertifyRepository extends JpaRepository<Certify, Integer> {

	public Optional<Certify> findByEmail(String email);

	void deleteByEmail(String email);

}
