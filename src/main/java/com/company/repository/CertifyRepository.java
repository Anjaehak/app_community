package com.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Certify;

import java.util.List;


public interface CertifyRepository extends JpaRepository<Certify, Integer>{

	Optional<Certify> findByEmail(String email);
}
