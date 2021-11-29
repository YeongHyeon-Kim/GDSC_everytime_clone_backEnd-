package seoultech.gdsc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import seoultech.gdsc.web.board.dto.BoardDto;
import seoultech.gdsc.web.entity.Board;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	Optional<List<Board>> findAllByCategoryId(int searchKeyword);

	Optional<List<Board>> findAllByIsHotEquals(boolean isHot);

	Optional<List<Board>> findTop2ByIsHotOrderByCreatedAtDesc(boolean isHot);

	@Query("select board from Board board where board.categoryId <=6 and board.createdAt IN (select max(board.createdAt) from board group by board.categoryId) order by board.categoryId")
	List<Board> getMyBoard();

//	@Query("select board from Board board where (board.categoryId <=6) order by (board.createdAt) desc limit 1")
//	List<Board> getMyBoard2();

	@Query(value = "select * from board where created_at > date_add(now(),interval-24 hour) and is_hot = true order by like_num+comment_num desc, created_at desc limit 2", nativeQuery = true)
	List<Board> getRealTime();

	List<Board> findAllByContentOrTitleContaining(String keyword1,String keyword2);

//	@Query("select board from Board board where board.categoryId=?1 and (board.content like %?2% or board.title like %?3%)")
//	Optional<List<Board>> searchByCategoryId(int category,String keyword1,String keyword2);

	@Query(value = "select * from board where category_id=?1 and (content like %?2% or title like %?2%)", nativeQuery = true)
	List<Board> searchByCategoryId(int category,String keyword1,String keyword2);
}
