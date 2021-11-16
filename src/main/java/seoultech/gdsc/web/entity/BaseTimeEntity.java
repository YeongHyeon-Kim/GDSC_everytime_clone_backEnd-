package seoultech.gdsc.web.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//JPA Entity 클래스들이 BaseTimeEntity를 상속할경우 필드들도 칼럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class)
//BaseTimeEntity클래스에 Auditing 기능을 포함시킨다.
//Auditing -> Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능
//https://webcoding-start.tistory.com/53
public abstract class BaseTimeEntity {
	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

}
