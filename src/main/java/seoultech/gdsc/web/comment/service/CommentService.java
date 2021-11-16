package seoultech.gdsc.web.comment.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.web.board.dto.BoardDto;
import seoultech.gdsc.web.comment.dto.CommentDto;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.CommentRepository;
import seoultech.gdsc.web.repository.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentService {
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public List<BoardDto.GetCommentRequest> getComment(int id){
		List<Comment> comments = commentRepository.findAllByBoardId_Id(id);
		List<BoardDto.GetCommentRequest> getCommentRequest = comments.stream().map(comment -> {
			BoardDto.GetCommentRequest getComment = modelMapper.map(comment, BoardDto.GetCommentRequest.class);
			getComment.setCreatedAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMddHHss")));
			if (comment.getIsSecret()){
				getComment.setNickname("익명");
				getComment.setProfilePic("익명 사진");
			}else{
				getComment.setNickname(comment.getUser().getNickname());
				getComment.setProfilePic(comment.getUser().getProfilePic());
			}
			getComment.setUserId(comment.getUser().getId());
			return getComment;
		}).collect(Collectors.toList());
		return getCommentRequest;
	}

	@Transactional
	public void saveComment(int id, CommentDto.SaveDto dto){
		Board board = boardRepository.getById(dto.getBoardId());
		User user = userRepository.getById(id);

		Comment newComment = Comment.builder()
				.board(board)
				.user(user)
				.content(dto.getContent())
				.isSecret(dto.getIsSecret())
				.build();
		commentRepository.save(newComment);
	}
}
