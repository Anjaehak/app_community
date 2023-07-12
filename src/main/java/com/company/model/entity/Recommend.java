package com.company.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "recommends", uniqueConstraints = {
		@UniqueConstraint(name = "recommends_01", columnNames = { "post_id", "user_id" }) })
@Data
public class Recommend {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post postsId;

	@ManyToOne
	@JoinColumn(name = "reply_id")
	private Reply repliesId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User usersId;

}
