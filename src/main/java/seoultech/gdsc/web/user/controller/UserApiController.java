package seoultech.gdsc.web.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.EmptyJsonResponse;
import seoultech.gdsc.web.response.FailResponse;
import seoultech.gdsc.web.response.SuccessResponse;
import seoultech.gdsc.web.user.dto.*;
import seoultech.gdsc.web.user.service.UserService;

import javax.persistence.Basic;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {
	private final UserService userService;
	private final HttpSession session;

	@PostMapping("")
	public BasicResponse saveUser(@RequestBody UserDto.SaveRequest user) {
		String message = userService.userJoin(user);
		if (message.equals("")) {
			return new SuccessResponse<>(new EmptyJsonResponse());
		} else {
			return new FailResponse<>(message, new EmptyJsonResponse());
		}
	}

	@GetMapping("")
	public BasicResponse getUser(){

		Object id = session.getAttribute("logInUserInfo");
		if (id==null){
			return new FailResponse<>("", new EmptyJsonResponse());
		}else{
			UserDto.GetRequest userDto = userService.getUserInfo((int)id);
			return new SuccessResponse<>(userDto);
		}
	}

	@PutMapping("")
	public BasicResponse changeNick(@RequestBody User user){
		int id = (int) session.getAttribute("logInUserInfo");
		Optional<User> updateUser = userService.changeNick(id, user.getNickname());
		if (updateUser.isEmpty()){
			return new FailResponse<>("닉네임이 중복되었습니다", new EmptyJsonResponse());
		}else{
			return new SuccessResponse<>(new EmptyJsonResponse());
		}

	}
	@DeleteMapping("")
	public BasicResponse deleteUser(){
		int id = (int) session.getAttribute("logInUserInfo");
		userService.deleteUser(id);
		session.removeAttribute("logInUserInfo");
		return new SuccessResponse<>(new EmptyJsonResponse());
	}

	@PostMapping("/login")
	public BasicResponse login(@RequestBody LoginDto loginDto){
		int userId = userService.login(loginDto);
		if(userId==0){
			return new FailResponse<>("일치하지 않는 회원정보 입니다.", new EmptyJsonResponse());
		}else{
			session.setAttribute("logInUserInfo", userId);
			return new SuccessResponse<>(new EmptyJsonResponse());
		}
	}

	@GetMapping("/logout")
	public BasicResponse logout(){
		session.removeAttribute("logInUserInfo");
		return new SuccessResponse<>(new EmptyJsonResponse());
	}
}
