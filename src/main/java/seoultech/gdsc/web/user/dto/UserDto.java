package seoultech.gdsc.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UserDto {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class SaveRequest{
		private String userId;
		private String password;
		private String email;
		private String name;
		private String nickname;
		private String major;
		private String hp;
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class GetRequest {
		private String userId;
		private String email;
		private String name;
		private String nickname;
		private boolean isAuth;
		private String profilePic;
	}

}
