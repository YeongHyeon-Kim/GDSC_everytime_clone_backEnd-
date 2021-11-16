package seoultech.gdsc.web.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import seoultech.gdsc.web.entity.Liked;
import seoultech.gdsc.web.entity.User;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LikedRepositoryTest {
	@Autowired
	private LikedRepository likedRepository;

	@Autowired
	private UserRepository userRepository;

	private Liked newLiked;
	private User user;

	@Test
	public void saveLikedTest(){
		user = userRepository.findById(1).get();

		newLiked = Liked.builder()
				.likeCategory(1)
				.refId(1)
				.user(user)
				.build();
		likedRepository.save(newLiked);

	}
	@Test
	public void getLikedTest(){
		Optional<Liked> liked= likedRepository.findById(1);
		liked.ifPresent(selectLiked->{
			System.out.println("Liked Category : "+selectLiked.getLikeCategory());
			System.out.println("Liked RefId : "+selectLiked.getRefId());
			System.out.println("Liked userID : "+selectLiked.getUser());
			System.out.println("Liked createdAt : "+selectLiked.getCreatedAt());
			System.out.println("Liked updatedAt : "+selectLiked.getUpdatedAt());
		});
	}

	@Test
	public void deleteBoardCategoryTest(){
		likedRepository.deleteById(1);
	}


	@Test
	public void changeBoardCategoryTest(){
		Optional<Liked> liked= likedRepository.findById(1);
		liked.ifPresent(selectLiked->{
			selectLiked.setLikeCategory(2);
			likedRepository.save(selectLiked);
		});

		Optional<Liked> newLiked= likedRepository.findById(1);
		newLiked.ifPresent(selectLiked->{
			System.out.println("Liked Category : "+selectLiked.getLikeCategory());
			System.out.println("Liked RefId : "+selectLiked.getRefId());
			System.out.println("Liked userID : "+selectLiked.getUser());
			System.out.println("Liked createdAt : "+selectLiked.getCreatedAt());
			System.out.println("Liked updatedAt : "+selectLiked.getUpdatedAt());
		});
	}
}
