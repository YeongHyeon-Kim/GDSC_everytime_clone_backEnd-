package seoultech.gdsc.web.board.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.web.board.dto.BoardDto;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.CommentRepository;
import seoultech.gdsc.web.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	@Transactional(readOnly = true)
	//readOnly = true를 추가하면 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 죄회 속도가 개선된다.
	//등록,수정,삭제 기능이 전혀 없는 서비스 메소드에서 사용하는 것을 추천한다.
	public List<BoardDto.ResponseDto>  findAllByCategory(int category){
		Optional<List<Board>> boards;
		if (category==7){
			boards =  boardRepository.findAllByIsHotEquals(true);

		}else {
			boards = boardRepository.findAllByCategoryId(category);
		}
		if (boards.isPresent()){
			List<BoardDto.ResponseDto> response = boards.get().stream().map(board -> {
				BoardDto.ResponseDto boardDto = modelMapper.map(board, BoardDto.ResponseDto.class);
				boardDto.setCreatedAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMdd")));
				boardDto.setCategoryId(category);
				return boardDto;
			}).collect(Collectors.toList());
			return response;
		}
		return null;
	}

	@Transactional(readOnly = true)
	public BoardDto.GetRequest getDetail(int id){

		Board board =  boardRepository.getById(id);
		BoardDto.GetRequest getBoard = modelMapper.map(board, BoardDto.GetRequest.class);
		getBoard.setCreatedAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMdd")));
		if (board.getIsSecret()){
			getBoard.setNickname("익명");
			getBoard.setProfilePic("익명사진");
		}else{
			getBoard.setNickname(board.getUser().getNickname());
			getBoard.setProfilePic(board.getUser().getProfilePic());
		}
		return getBoard;
	}

	@Transactional
	public void saveBoard(int id, BoardDto.SaveRequest dto){
		User user = userRepository.getById(id);
		Board newBoard = Board.builder()
					.user(user)
					.title(dto.getTitle())
					.content(dto.getContent())
					.categoryId(dto.getCategoryId())
					.build();
		boardRepository.save(newBoard);
	}

	@Transactional
	public List<BoardDto.myBoard> getMyBoard(){
		List<Board> myBoard = boardRepository.getMyBoard();
		List<BoardDto.myBoard> response = myBoard.stream().map(board ->{
			BoardDto.myBoard boardDto = modelMapper.map(board,BoardDto.myBoard.class);
			boardDto.setCreatedAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMdd")));
			return boardDto;
		}).collect(Collectors.toList());
		return response;
	}

	@Transactional
	public List<BoardDto.HotResponseDto> getMainHot(){
		Optional<List<Board>> getBoard = boardRepository.findTop2ByIsHotOrderByCreatedAtDesc(true);
		if(getBoard.isPresent()){
			List<BoardDto.HotResponseDto> response = getBoard.get().stream().map(board -> {
				BoardDto.HotResponseDto boardDto = modelMapper.map(board, BoardDto.HotResponseDto.class);
				boardDto.setCreatedAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMdd")));
				return boardDto;
			}).collect(Collectors.toList());
			return response;
		}
		return null;
	}

	@Transactional
	public List<BoardDto.RealtimeResponseDto> getRealTime(){
		List<Board> board = boardRepository.getRealTime();
		List<BoardDto.RealtimeResponseDto> response = board.stream().map(boards -> {
			String nickname;
			String profilePic;
			if (boards.getIsSecret()){
				nickname = "익명";
				profilePic = "익명 프로필";
			}else{
				nickname = boards.getUser().getNickname();
				profilePic = boards.getUser().getProfilePic();
			}
			BoardDto.RealtimeResponseDto boardDto = modelMapper.map(boards, BoardDto.RealtimeResponseDto.class);
			boardDto.setCreatedAt(boards.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMdd")));
			boardDto.setNickname(nickname);
			boardDto.setProfilePic(profilePic);
			return boardDto;
		}).collect(Collectors.toList());
		return response;
	}
//	@Transactional
//	public List<BoardDto.ResponseDto> mainFilter(int category, boolean isHot){
//
//	}

	@Transactional
	public List<BoardDto.SearchResponseDto> search(int category, String keyword){
		List<Board> board;
		if (category==0){
			board = boardRepository.findAllByContentOrTitleContaining(keyword,keyword);
		}else{
			board = boardRepository.searchByCategoryId(category,keyword,keyword);
		}
		if (!board.isEmpty()) {
			List<BoardDto.SearchResponseDto> response = board.stream().map(boards -> {
				String nickname;
				if (boards.getIsSecret()) {
					nickname = "익명";
				} else {
					nickname = boards.getUser().getNickname();
				}
				BoardDto.SearchResponseDto boardDto = modelMapper.map(boards, BoardDto.SearchResponseDto.class);
				boardDto.setCreatedAt(boards.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMdd")));
				boardDto.setNickname(nickname);
				return boardDto;
			}).collect(Collectors.toList());
			return response;
		}else{
			return null;
		}
	}
}
