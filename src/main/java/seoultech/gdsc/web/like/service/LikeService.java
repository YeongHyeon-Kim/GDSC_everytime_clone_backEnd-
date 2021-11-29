package seoultech.gdsc.web.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.Liked;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.like.dto.LikeDto;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.CommentRepository;
import seoultech.gdsc.web.repository.LikedRepository;
import seoultech.gdsc.web.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final LikedRepository likedRepository;
	@Transactional
	public String click(int id, LikeDto.click click){
		int refId = click.getRefId();
		int categoryId = click.getCategory();
		User user = userRepository.getById(id);
		Optional<Liked> check = likedRepository.findByRefIdAndUserAndLikeCategory(refId, user, categoryId);

		if (check.isEmpty()){
			Liked liked = Liked.builder().likeCategory(categoryId).user(user).refId(refId).build();
			likedRepository.save(liked);
			if (refId==1){
				//board에 +1
				Board board = boardRepository.getById(categoryId);
				int now = board.getLikeNum()+1;
				board.setLikeNum(now);
				if (now >=10){
					board.setIsHot(true);
				}
				boardRepository.save(board);
			}else if(refId==2){
				Comment comment = commentRepository.getById(categoryId);
				int now = comment.getLikeNum()+1;
				comment.setLikeNum(now);
				commentRepository.save(comment);
			}
		}else{
			if (refId==1) {
				return "이미 공감한 글입니다.";
			}else if (refId==2){
				return "이미 공감한 댓글입니다.";
			}
		}
		return "";
	}

}
