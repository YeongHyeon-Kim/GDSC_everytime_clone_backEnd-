package seoultech.gdsc.web.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.User;


import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentRepositoryTest {
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	private Comment newComment;
	private Board board;
	private User user;

	@Test
	public void saveCommentTest(){
		board = boardRepository.findById(1).get();
		user = userRepository.findById(1).get();

		newComment = Comment.builder()
				.board(board)
				.user(user)
				.content("테스트 코멘트")
				.build();
		commentRepository.save(newComment);
	}
	@Test
	public void getCommentTest(){
		Optional<Comment> comment= commentRepository.findById(1);
		comment.ifPresent(selectComment->{
			System.out.println("Comment boardID : "+selectComment.getBoard());
			System.out.println("Comment userID : "+selectComment.getUser());
			System.out.println("Comment Content : "+selectComment.getContent());
			System.out.println("Comment isSecret : "+selectComment.getIsSecret());
			System.out.println("Comment likeNum : "+selectComment.getLikeNum());
			System.out.println("Comment createdAt : "+selectComment.getCreatedAt());
			System.out.println("Comment updatedAt : "+selectComment.getUpdatedAt());
		});
	}

	@Test
	public void deleteCommentTest(){
		commentRepository.deleteById(1);
	}


	@Test
	public void changeCommentTest(){
		Optional<Comment> comment = commentRepository.findById(1);
		comment.ifPresent(selectComment->{
			selectComment.setContent("바뀐 코멘트 콘텐츠");
			commentRepository.save(selectComment);
		});

		Optional<Comment> changeComment= commentRepository.findById(1);
		changeComment.ifPresent(selectComment->{
			System.out.println("Comment boardID : "+selectComment.getBoard());
			System.out.println("Comment userID : "+selectComment.getUser());
			System.out.println("Comment Content : "+selectComment.getContent());
			System.out.println("Comment isSecret : "+selectComment.getIsSecret());
			System.out.println("Comment likeNum : "+selectComment.getLikeNum());
			System.out.println("Comment createdAt : "+selectComment.getCreatedAt());
			System.out.println("Comment updatedAt : "+selectComment.getUpdatedAt());
		});
	}
}
