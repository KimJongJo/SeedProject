package seed.project.Manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Seed {

	private int seedNo;			// 씨앗 번호
	private String seedName;	// 씨앗 이름
	private String seedImgPath;	// 씨앗 이미지 경로
	private	String seedPrice;	// 씨앗 가격
	private String seedTemp;	// 온도
	private String seedTime;	// 시기
	private String seedDistance;// 재식 거리
	private String seedRate;	// 발아율
	private String seedSoldOut;	// 품절 여부
	private int seedCode;		// 씨앗 코드
	
}
