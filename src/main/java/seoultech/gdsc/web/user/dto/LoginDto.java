package seoultech.gdsc.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seoultech.gdsc.web.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginDto {
	private String userId;
	private String password;


}