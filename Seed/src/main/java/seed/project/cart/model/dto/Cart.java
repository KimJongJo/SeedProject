package seed.project.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Cart {
	
	private int cartNo;		// 장바구니 번호
	private int memberNo;	// 회원 번호
	private int seedNo;		// 씨앗 번호
	private int count;		// 수량
	
	private String seedName;	// 씨앗 이름
	private String seedPrice;	// 씨앗 가격
	private String seedImgPath;	// 이미지 경로
	
}
