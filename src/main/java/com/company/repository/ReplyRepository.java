package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.model.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
