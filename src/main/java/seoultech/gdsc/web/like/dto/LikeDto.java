package seoultech.gdsc.web.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LikeDto {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class click{
		private int refId;
		private int category;
	}
}
