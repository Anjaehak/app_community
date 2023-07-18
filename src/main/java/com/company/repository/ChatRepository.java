package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Chat;
import com.company.model.entity.User;


public interface ChatRepository extends JpaRepository<Chat, Integer> {
	
	void deleteAllByUsersId(User user);


}
