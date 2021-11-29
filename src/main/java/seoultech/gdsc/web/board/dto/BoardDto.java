package seoultech.gdsc.web.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seoultech.gdsc.web.entity.User;

import java.time.LocalDateTime;

public class BoardDto {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class ResponseDto{
		private int categoryId;
		private String title;
		private String content;
		private int likeNum;
		private int commentNum;
		private String createdAt;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class GetRequest{
		private int id;
		private String title;
		private String content;
		private String nickname;
		private String profilePic;
		private int likeNum;
		private int commentNum;
		private String createdAt;
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class SaveRequest{
		private int categoryId;
		private String content;
		private String title;
		private Boolean isSecret;
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class GetCommentRequest{
		private int id;
		private String nickname;
		private String profilePic;
		private String content;
		private int likeNum;
		private int userId;
		private String createdAt;
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class myBoard{
		private int id;
		private int categoryId;
		private String title;
		private String content;
		private String createdAt;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class HotResponseDto{
		private int id;
		private String title;
		private String content;
		private int likeNum;
		private int commentNum;
		private String createdAt;
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class RealtimeResponseDto{
		private int id;
		private String title;
		private String content;
		private int likeNum;
		private int commentNum;
		private String createdAt;
		private String nickname;
		private String profilePic;
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class SearchResponseDto{
		private int id;
		private String title;
		private String content;
		private int likeNum;
		private int commentNum;
		private String createdAt;
		private String nickname;
	}
}
