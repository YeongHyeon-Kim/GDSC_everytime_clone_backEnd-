package seoultech.gdsc.web.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
//기본 생성자 자동추가, public User(){}와 같은 효과
//@AllArgsConstructor
//모든 필드 값을 파라미터로 받는 생성자를 만듦 --> builder로 구현
@Builder //https://mangkyu.tistory.com/163 builder 패턴의 장점
@AllArgsConstructor //https://yuja-kong.tistory.com/99 -->  no arg 말고 이게 필요한 이유
@Entity
public class User extends BaseTimeEntity{
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//PK의 생성 규칙을 나타낸다.
	//GenerationType.IDENTITY 옵션을 추가해야 auto increment 가 된다
	private int id;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String hp;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String major;


	private String profilePic;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE") // https://m.blog.naver.com/yjhyjh5369/221997271213
	@Builder.Default
	private Boolean isAuth = Boolean.FALSE;

	public void update(String nickname){
		this.nickname = nickname;
	}
}
