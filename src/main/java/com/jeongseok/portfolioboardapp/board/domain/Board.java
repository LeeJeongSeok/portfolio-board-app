package com.jeongseok.portfolioboardapp.board.domain;

import com.jeongseok.portfolioboardapp.comment.domain.Comment;
import com.jeongseok.portfolioboardapp.type.UseType;
import com.jeongseok.portfolioboardapp.user.domain.BaseTimeEntity;
import com.jeongseok.portfolioboardapp.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_index")
	private long boardIndex;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id") // PK가 아닌 컬럼과 연관관계를 매핑할 때 referencedColumnName 속성을 사용
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "use_yn")
	private UseType useYn;

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	@Where(clause = "use_yn = 'Y'")
	@OrderBy("createdAt desc")
	private List<Comment> comments = new ArrayList<>();

	public void update(String title, String content) {
		this.title = title;
		this.content = content;

	}

	public void delete(UseType useYn) {
		this.useYn = useYn;
	}
}
