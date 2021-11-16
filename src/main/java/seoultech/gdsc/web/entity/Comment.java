package seoultech.gdsc.web.entity;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment extends BaseTimeEntity{
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(targetEntity = Board.class)
	private Board board;

	@ManyToOne(targetEntity = User.class)
	private User user;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT 1")
	@Builder.Default
	private Boolean isSecret = true;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@Builder.Default
	private int likeNum = 0;

}
