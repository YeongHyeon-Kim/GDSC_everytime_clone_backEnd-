package seoultech.gdsc.web.entity;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Message extends BaseTimeEntity{
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(targetEntity = User.class)
	private User fromUser;

	@ManyToOne(targetEntity = User.class)
	private User toUser;

	@Column(nullable = false)
	private String content;
}
