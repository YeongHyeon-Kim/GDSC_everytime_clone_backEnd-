package seoultech.gdsc.web.message.controller;

import lombok.RequiredArgsConstructor;
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
		String message = messageService.sendMessage(id, dto);
		if (message.equals("")){
			return new SuccessResponse<>(dto.getId());
		}else{
			return new FailResponse<>(message, new EmptyJsonResponse());
		}

	}
}
