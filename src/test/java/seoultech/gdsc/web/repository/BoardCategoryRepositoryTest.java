package seoultech.gdsc.web.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import seoultech.gdsc.web.WebApplicationTests;
import seoultech.gdsc.web.entity.BoardCategory;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardCategoryRepositoryTest extends WebApplicationTests {

	@Autowired
	private BoardCategoryRepository boardCategoryRepository;


	private BoardCategory newBoardCategory;

	@Test
	public void saveBoardCategoryTest(){
		newBoardCategory = BoardCategory.builder().
				categoryName("홍보 게시판")
				.build();
		boardCategoryRepository.save(newBoardCategory);
	}
	@Test
	public void getBoardCategoryTest(){
		Optional<BoardCategory> boardCategory = boardCategoryRepository.findById(1);
		boardCategory.ifPresent(selectBoardCategory->{
			System.out.println("Board Category : "+selectBoardCategory.getCategoryName());
		});
	}

	@Test
	public void deleteBoardCategoryTest(){
		boardCategoryRepository.deleteById(1);
	}


	@Test
	public void changeBoardCategoryTest(){
		Optional<BoardCategory> boardCategory = boardCategoryRepository.findById(1);
		boardCategory.ifPresent(selectBoardCategory->{
			selectBoardCategory.setCategoryName("바뀐 테스트 카테고리");
			boardCategoryRepository.save(selectBoardCategory);
		});

		Optional<BoardCategory> changeBoardCategory = boardCategoryRepository.findById(1);
		changeBoardCategory.ifPresent(selectBoardCategory->{
			System.out.println("Board Category : "+selectBoardCategory.getCategoryName());
		});
	}

}
