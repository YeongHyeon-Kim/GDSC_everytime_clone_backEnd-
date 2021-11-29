package seoultech.gdsc.web.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.web.like.dto.LikeDto;
import seoultech.gdsc.web.like.service.LikeService;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.EmptyJsonResponse;
import seoultech.gdsc.web.response.FailResponse;
import seoultech.gdsc.web.response.SuccessResponse;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like")
public class LikeApiController {
	private final LikeService likeService;
	private final HttpSession session;

	@PostMapping("")
	public BasicResponse clickLike(@RequestBody LikeDto.click click){
		int id = (int) session.getAttribute("logInUserInfo");
		String message = likeService.click(id,click);

		if (message.equals("")){
			return new SuccessResponse<>(message);
		}else{
			return new FailResponse<>(message, new EmptyJsonResponse());
		}
	}
}
