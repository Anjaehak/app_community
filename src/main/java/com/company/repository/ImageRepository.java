package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer>{

}
