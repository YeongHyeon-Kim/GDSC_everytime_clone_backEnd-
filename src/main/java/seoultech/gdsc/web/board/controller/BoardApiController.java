package seoultech.gdsc.web.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.web.board.dto.BoardDto;
import seoultech.gdsc.web.board.service.BoardService;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.EmptyJsonResponse;
import seoultech.gdsc.web.response.SuccessResponse;

import javax.persistence.Basic;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardApiController {
	private final BoardService boardService;
	private final HttpSession session;

	@GetMapping("")
	public BasicResponse findByCategory(@RequestParam(value= "category") int category){
		List<BoardDto.ResponseDto> board = boardService.findAllByCategory(category);
		String message;
		if (board==null){
			message = "해당 게시판에 글이 없습니다.";
			return new SuccessResponse<>(message);
		}
		return new SuccessResponse<>(board);
	}

	@GetMapping("/detail/{id}")
	public BasicResponse getDetail(@PathVariable int id){
		BoardDto.GetRequest getBoard = boardService.getDetail(id);
		return new SuccessResponse<>(getBoard);
	}

	@PostMapping("")
	public BasicResponse saveBoard(@RequestBody BoardDto.SaveRequest dto){
		int id = (int) session.getAttribute("logInUserInfo");
		boardService.saveBoard(id, dto);
		return new SuccessResponse<>(new EmptyJsonResponse());
	}

	@GetMapping("/main/myboard")
	public BasicResponse getMyBoard(){
		List<BoardDto.myBoard> myBoard = boardService.getMyBoard();
		return new SuccessResponse<>(myBoard);
	}

	@GetMapping("/main/hot")
	public BasicResponse getMainHot(){
		List<BoardDto.HotResponseDto> board = boardService.getMainHot();
		String message;
		if (board==null){
			message = "해당 게시판에 글이 없습니다.";
			return new SuccessResponse<>(message);
		}
		return new SuccessResponse<>(board);
	}

	@GetMapping("/main/realtime")
	public BasicResponse getRealTime(){
		List<BoardDto.RealtimeResponseDto> board = boardService.getRealTime();
		return new SuccessResponse<>(board);
	}
	//나중에 완성
//	@GetMapping("/main/filter")
//	public BasicResponse mainFilter(@RequestParam(value= "category") int category,@RequestParam(value= "hot") boolean isHot){
//		List<BoardDto.ResponseDto> board = boardService.mainFilter(category, isHot);
//		return new SuccessResponse<>(board);
//
//	}

	@GetMapping("/search")
	public BasicResponse search(@RequestParam(value= "category") int category,@RequestParam(value= "keyword") String keyword){
		List<BoardDto.SearchResponseDto> board = boardService.search(category, keyword);
		if (board!=null){
			return new SuccessResponse<>(board);
		}else{
			System.out.println("여기");
			return new SuccessResponse<>("관련된 게시글이 없습니다.");

		}


	}
}
