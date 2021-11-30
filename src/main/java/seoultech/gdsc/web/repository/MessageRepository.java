package seoultech.gdsc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import seoultech.gdsc.web.entity.Message;
import seoultech.gdsc.web.entity.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	@Query(value="select * from message where id\n" +
			"    in(\n" +
			"        select id\n" +
			"        from(\n" +
			"            select least(from_user_id, to_user_id) as user1, greatest(from_user_id, to_user_id) as user2, max(id) as id\n" +
			"            from message\n" +
			"            where from_user_id = ?1 or to_user_id = ?1\n" +
			"            group by user1, user2\n" +
			"            ) as subtable\n" +
			"    )order by created_at desc",nativeQuery = true)
	List<Message> getMessage(int id_num);


	List<Message> findAllByFromUserAndToUserOrToUserAndFromUserOrderByCreatedAtDesc(User from_user, User to_user_id, User to_user_id2, User from_user_id2);
}
