package seoultech.gdsc.web.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.web.board.dto.BoardDto;
import seoultech.gdsc.web.board.service.BoardService;
import seoultech.gdsc.web.comment.dto.CommentDto;
import seoultech.gdsc.web.comment.service.CommentService;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.EmptyJsonResponse;
import seoultech.gdsc.web.response.SuccessResponse;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
	private final CommentService commentService;
	private final HttpSession session;


	@GetMapping("/api/board/{id}/comment")
	public BasicResponse getComment(@PathVariable int id){
		List<BoardDto.GetCommentRequest> commentList = commentService.getComment(id);
		return new SuccessResponse<>(commentList);
	}

	@PostMapping("/api/board/comment")
	public BasicResponse saveComment(@RequestBody CommentDto.SaveDto dto){
		int id = (int) session.getAttribute("logInUserInfo");
		commentService.saveComment(id, dto);
		return new SuccessResponse<>(new EmptyJsonResponse());
	}
}
