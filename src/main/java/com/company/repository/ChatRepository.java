package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
