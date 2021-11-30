package seoultech.gdsc.web.message.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.Message;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.message.dto.MessageDto;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.CommentRepository;
import seoultech.gdsc.web.repository.MessageRepository;
import seoultech.gdsc.web.repository.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {
	private final MessageRepository messageRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public List<MessageDto.getMessageDto> getMessage(int id){
		List<Message> messages = messageRepository.getMessage(id);
		List<MessageDto.getMessageDto> listMessage = messages.stream().map(message ->{
			MessageDto.getMessageDto getMessage = modelMapper.map(message, MessageDto.getMessageDto.class);
			getMessage.setCreatedAt(message.getCreatedAt().format(DateTimeFormatter.ofPattern("yyMMddHHmm")));
			getMessage.setIs_mine(message.getFromUser().getId() == id);
			getMessage.setMessage_id(message.getId());
			return getMessage;
		}).collect(Collectors.toList());
		return listMessage;
	}

	@Transactional
	public List<MessageDto.getMessageDto> getDetailMessage(int id, int message_id){
		User from_user = userRepository.getById(id);
		User to_user = messageRepository.getById(message_id).getToUser();
		List<Message> messages = messageRepository.findAllByFromUserAndToUserOrToUserAndFromUserOrderByCreatedAtDesc(from_user, to_user,from_user, to_user);
		List<MessageDto.getMessageDto> listMessage = messages.stream().map(message->{
			MessageDto.getMessageDto getMessage = modelMapper.map(message, MessageDto.getMessageDto.class);
			getMessage.setIs_mine(message.getFromUser().getId() == id);
			getMessage.setMessage_id(message.getId());
			return getMessage;
		}).collect(Collectors.toList());
		return listMessage;
	}

	@Transactional
	public int sendMessage(int id, MessageDto.sendMessageDto dto){

		User fromUser = userRepository.getById(id);
		int message;
		User toUser;
		int toUserId;
		if(dto.getGroup() == 0){
			toUser = commentRepository.getById(dto.getId()).getUser();
			toUserId = toUser.getId();
			message = checkId(id, dto, toUserId);
		}else if (dto.getGroup() == 1){
			toUser = messageRepository.getById(dto.getId()).getToUser();
			toUserId = toUser.getId();
			message = checkId(id, dto, toUserId);
		}else{
			toUser = boardRepository.getById(dto.getId()).getUser();
			toUserId = toUser.getId();
			message = checkId(id, dto, toUserId);
		}

		if (message==0){
			Message newMessage = Message.builder().content(dto.getContent()).fromUser(fromUser).toUser(toUser).build();
			messageRepository.save(newMessage);
			System.out.println(newMessage.getId());
			return newMessage.getId();
		}else{
			return message;
		}
	}

	public int checkId(int id, MessageDto.sendMessageDto dto, int toUserId){
		Optional<User> toUser = userRepository.findById(toUserId);
		if (toUser.isEmpty()){
			return 1;
		}
		User getToUser = toUser.get();
		if(getToUser.getId()==id){
			return 2;
		}
		return 0;
	}
}
