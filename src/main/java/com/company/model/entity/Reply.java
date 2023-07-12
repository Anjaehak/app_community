package com.company.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "replies")
@Data
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post postsId;

	private Integer parentId;
	
	@ManyToOne
	@JoinColumn(name = "user_nick")
	private User replyWriter;

	private String replyContent;
	private LocalDateTime replyDate;
	
	@PrePersist
	public void prePersist() {
		this.replyDate = LocalDateTime.now();
	}

}
