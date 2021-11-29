package seoultech.gdsc.web.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

public class MessageDto {

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class getMessageDto {
		private Boolean is_mine;
		private int message_id;
		private String content;
		private String createdAt;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class sendMessageDto {
		private int id;
		private String content;
		private int group;
	}
}