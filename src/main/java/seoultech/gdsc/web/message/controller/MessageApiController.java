package seoultech.gdsc.web.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.web.entity.Message;
import seoultech.gdsc.web.message.dto.MessageDto;
import seoultech.gdsc.web.message.service.MessageService;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.EmptyJsonResponse;
import seoultech.gdsc.web.response.FailResponse;
import seoultech.gdsc.web.response.SuccessResponse;

import javax.servlet.http.HttpSession;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageApiController {
	private final HttpSession session;
	private final MessageService messageService;

	@GetMapping("")
	public BasicResponse getMessage(){
		int id = (int) session.getAttribute("logInUserInfo");
		List<MessageDto.getMessageDto> getMessage = messageService.getMessage(id);
		return new SuccessResponse<>(getMessage);
	}

	@GetMapping("/detail/{message_id}")
	public BasicResponse getMessage(@PathVariable int message_id){
		int id = (int) session.getAttribute("logInUserInfo");
		List<MessageDto.getMessageDto> getMessage = messageService.getDetailMessage(id, message_id);
		return new SuccessResponse<>(getMessage);
	}

	@PostMapping("")
	public BasicResponse sendMessage(@RequestBody MessageDto.sendMessageDto dto){
		int id = (int) session.getAttribute("logInUserInfo");
		int message = messageService.sendMessage(id, dto);
		if (message==2){
			return new FailResponse<>("자기 자신에게 쪽지를 보낼 수 없습니다", new EmptyJsonResponse());
		}else if (message==1)
		{
			return new FailResponse<>("탈퇴한 사용자에게 쪽지를 보낼 수 없습니다", new EmptyJsonResponse());
		}
		else{
			int messageId = message;
			return new SuccessResponse<>(messageId);
		}



	}
}
