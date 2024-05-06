package seed.project.myPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Address {
	
	private int addressNo;        // 주소 번호
	private int memberNo;         // 회원 번호
	private String memberAddress; // 회원 주소
	
	
	
	
	
	
}
