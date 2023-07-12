package com.company.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String email;
	private String password;
	private String nick;
	private String userImage;
	private LocalDateTime joinDate;
	private String authority;
	private String socialToken;

	// 유저가 작성한 글 모음
	@OneToMany(mappedBy = "postWriter")
	private List<Post> posts;

	// 유저가 작성한 댓글 모음
	@OneToMany(mappedBy = "replyWriter")
	private List<Reply> replies;

	// 유저가 추천한 글 모음
	@OneToMany(mappedBy = "usersId")
	private List<Recommend> recommends;

	// 유저가 등록한 채팅 모음
	@OneToMany(mappedBy = "usersId")
	private List<Chat> chats;

	@PrePersist
	public void prePersist() {
		this.joinDate = LocalDateTime.now();
	}
	
}
