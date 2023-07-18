package com.company.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "replyRecommends", uniqueConstraints = {
		@UniqueConstraint(name = "recommends_01", columnNames = { "reply_id", "user_id" }) })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRecommend {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "reply_id")
	private Reply repliesId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User usersId;

}
