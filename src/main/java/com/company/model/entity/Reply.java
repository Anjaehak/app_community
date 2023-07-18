package com.company.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "replies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post postsId;

	private Integer parentId;

	@ManyToOne
	@JoinColumn(name = "user_nick")
	private User replyWriter;

	private String replyContent;
	private LocalDateTime replyDate;

	@OneToMany(mappedBy = "repliesId")
	private List<ReplyRecommend> recommends;

	@PrePersist
	public void prePersist() {
		this.replyDate = LocalDateTime.now();
	}

}
