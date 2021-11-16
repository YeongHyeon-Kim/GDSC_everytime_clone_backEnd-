package seoultech.gdsc.web.entity;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board extends BaseTimeEntity{

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(targetEntity = User.class)
//	@JoinColumn --> db 만들때 fk 제한 안걸었으면 적어줘야 하는듯? 아니면 자동으로 연결 --> db 만들때 제한 했어도 적어줘야함,, --> 애초에 디비를 미리 만들어두지 않아도 됨..
	private User user;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private int categoryId;

	@Column(nullable = true)
	private String imageUrl;

	@Column(nullable = false , columnDefinition = "BOOLEAN DEFAULT TRUE") //왜 true 로 기본 했지??
	@Builder.Default
	private Boolean isSecret = true;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@Builder.Default
	private int likeNum = 0;

	@Column(nullable = true, columnDefinition = "INT DEFAULT 0")
	@Builder.Default
	private int commentNum = 0;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	@Builder.Default
	private Boolean isHot = false;


}
