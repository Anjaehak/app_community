package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

}
