package seoultech.gdsc.web.entity;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Liked extends BaseTimeEntity{
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int likeCategory;

	@Column(nullable = false)
	private int refId;

	@ManyToOne(targetEntity = User.class)
	private User user;
}
