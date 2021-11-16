package seoultech.gdsc.web.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.web.WebApplicationTests;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.User;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardRepositoryTest extends WebApplicationTests {
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	private User newUser;
	private User queryUser;
	private Board newBoard;


	@Test
	public void saveBoardTest(){
		Optional<User> newUser = userRepository.findById(1);
		newUser.ifPresent(getUser -> {
			newBoard = Board.builder()
					.user(getUser)
					.title("메인 핫 게시판 테스트3")
					.content("테스트 게시글~~")
					.categoryId(2)
					.isHot(true)
					.commentNum(0)
					.build();
		});
		boardRepository.save(newBoard);
	}

	@Test
	@Transactional
	public void getBoard(){
		Optional<Board> newBoard = boardRepository.findById(2);
		newBoard.ifPresent(getBoard->{
			System.out.println("user : " + getBoard.getUser());
			System.out.println("title : " + getBoard.getTitle());
			System.out.println("content : " + getBoard.getContent());
			System.out.println("category : " + getBoard.getCategoryId());
			System.out.println("isSecret : " + getBoard.getIsSecret());
			System.out.println("likeNum : " + getBoard.getLikeNum());
			System.out.println("comment Num : " + getBoard.getCommentNum());
			System.out.println("is hot : " + getBoard.getIsHot());
			System.out.println("created at : " + getBoard.getCreatedAt());
			System.out.println("updated at : " + getBoard.getUpdatedAt());
		});

	}

	@Test
	public void deleteBoardTest(){
		boardRepository.deleteById(8);
	}

	@Test
	public void changeBoardTest(){
		Optional<Board> changeBoard = boardRepository.findById(2);
		String changeTitle = "바뀐 제목";
		changeBoard.ifPresent(getBoard ->{
			getBoard.setTitle(changeTitle);
			boardRepository.save(getBoard);
		});
		Optional<Board> newBoard = boardRepository.findById(2);
		newBoard.ifPresent(getBoard->{
			System.out.println("user id : " + getBoard.getUser());
			System.out.println("title : " + getBoard.getTitle());
			System.out.println("content : " + getBoard.getContent());
			System.out.println("category : " + getBoard.getCategoryId());
			System.out.println("isSecret : " + getBoard.getIsSecret());
			System.out.println("likeNum : " + getBoard.getLikeNum());
			System.out.println("comment Num : " + getBoard.getCommentNum());
			System.out.println("is hot : " + getBoard.getIsHot());
			System.out.println("created at : " + getBoard.getCreatedAt());
			System.out.println("updated at : " + getBoard.getUpdatedAt());
		});
	}
}
