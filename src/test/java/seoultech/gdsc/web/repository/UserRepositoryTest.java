package seoultech.gdsc.web.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.web.WebApplicationTests;
import seoultech.gdsc.web.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest extends WebApplicationTests {

	@Autowired
	private UserRepository userRepository;

	private User newUser;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
//	@Transactional
	public void saveUserTest(){
		String password = "1234abcd";
		String encodedPassword = passwordEncoder.encode(password);

		newUser = User.builder()
				.userId("kim3")
				.email("insut2@naver.com")
				.name("김영현3")
				.hp("01047419394")
				.major("ITM")
				.nickname("kyh3")
				.password(encodedPassword)
				.build();
		userRepository.save(newUser);

		assertAll(
				() -> assertNotEquals(password, encodedPassword),
				() -> assertTrue(passwordEncoder.matches(password, encodedPassword))
		);
	}
	@Test
	@Transactional
	public void findUserTest2(){
		newUser = userRepository.getById(2);
		System.out.println("UserId : "+newUser.getUserId());
		System.out.println("UserPassword : "+newUser.getPassword());
		System.out.println("UserEmail : "+newUser.getEmail());
		System.out.println("UserName : "+newUser.getName());
		System.out.println("UserHp : "+newUser.getHp());
		System.out.println("UserMajor : "+newUser.getMajor());
		System.out.println("UserNickName : "+newUser.getNickname());
		System.out.println("UserIsAuth : "+newUser.getIsAuth());
		System.out.println("UserCreatedAt : "+newUser.getCreatedAt());
		System.out.println("UserUpdatedAt : "+newUser.getUpdatedAt());
	}
	@Test
	public void findUserTest(){
		Optional<User> getUser = userRepository.findById(2);
		getUser.ifPresent(selectUser ->{
			System.out.println("UserId : "+selectUser.getUserId());
			System.out.println("UserPassword : "+selectUser.getPassword());
			System.out.println("UserEmail : "+selectUser.getEmail());
			System.out.println("UserName : "+selectUser.getName());
			System.out.println("UserHp : "+selectUser.getHp());
			System.out.println("UserMajor : "+selectUser.getMajor());
			System.out.println("UserNickName : "+selectUser.getNickname());
			System.out.println("UserIsAuth : "+selectUser.getIsAuth());
			System.out.println("UserCreatedAt : "+selectUser.getCreatedAt());
			System.out.println("UserUpdatedAt : "+selectUser.getUpdatedAt());
		});

	}

	@Test
	public void deleteUserTest(){
		userRepository.deleteById(1);
	}

	@Test
	public void updateUserTest(){
		Optional<User> changeUser = userRepository.findById(2);
		String changeNick = "Change NickName";

		changeUser.ifPresent(selectUser ->{
			selectUser.setNickname(changeNick);
			userRepository.save(selectUser);
			System.out.println(selectUser.getNickname());
		});

		//check part 다시 가져와서 출력
		Optional<User> checkUser = userRepository.findById(2);
		checkUser.ifPresent(selectUser -> {
			System.out.println("UserId : "+selectUser.getUserId());
			System.out.println("UserNickName : "+selectUser.getNickname());
		});


	}

}
