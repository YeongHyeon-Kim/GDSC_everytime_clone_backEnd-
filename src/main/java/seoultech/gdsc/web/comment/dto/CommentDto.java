package seoultech.gdsc.web.comment.dto;

import lombok.*;

public class CommentDto {

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class SaveDto{
		private int boardId;
		private String content;
		private Boolean isSecret;
	}
}
