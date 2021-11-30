package seoultech.gdsc.web.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import seoultech.gdsc.web.entity.Message;
import seoultech.gdsc.web.entity.User;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageRepositoryTest {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserRepository userRepository;

	private Message newMessage;
	private User user1;
	private User user2;

	@Test
	public void saveMessageTest(){
		user1 = userRepository.findById(3).get();
		user2 = userRepository.findById(5).get();

		newMessage = Message.builder()
				.fromUser(user2)
				.toUser(user1)
				.content("5가 3한테 보내는 메세지 진짜")
				.build();
		messageRepository.save(newMessage);
	}

	@Test
	public void getMessageTest(){
		Optional<Message> message= messageRepository.findById(1);
		message.ifPresent(selectMessage->{
			System.out.println("Message FromId : "+selectMessage.getFromUser());
			System.out.println("Message ToId : "+selectMessage.getToUser());
			System.out.println("Message Content : "+selectMessage.getContent());
			System.out.println("Message createdAt : "+selectMessage.getCreatedAt());
			System.out.println("Message updatedAt : "+selectMessage.getUpdatedAt());
		});
	}

	@Test
	public void deleteMessageTest(){
		messageRepository.deleteById(1);
	}


	@Test
	public void changeBoardCategoryTest(){
		Optional<Message> message= messageRepository.findById(1);
		message.ifPresent(selectMessage->{
			selectMessage.setContent("바뀐 메세지");
			messageRepository.save(selectMessage);
		});

		Optional<Message> newMessage= messageRepository.findById(1);
		newMessage.ifPresent(selectMessage->{
			System.out.println("Message FromId : "+selectMessage.getFromUser());
			System.out.println("Message ToId : "+selectMessage.getToUser());
			System.out.println("Message Content : "+selectMessage.getContent());
			System.out.println("Message createdAt : "+selectMessage.getCreatedAt());
			System.out.println("Message updatedAt : "+selectMessage.getUpdatedAt());
		});
	}
}
