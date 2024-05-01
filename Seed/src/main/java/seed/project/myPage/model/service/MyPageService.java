package seed.project.myPage.model.service;

import java.util.List;
import java.util.Map;

import seed.project.member.model.dto.Member;

public interface MyPageService {

	/** 회원 정보 수정
	 * @param inputMember
	 * @return
	 */
	int updateInfo(Member inputMember);

	/** 회원 비밀번호 수정
	 * @param paramMap
	 * @param memberNo
	 * @return
	 */
	int updatePw(Map<String, Object> paramMap, int memberNo);

	

	/** 회원 주소 검색
	 * @param memberNo
	 * @return
	 */
	List<Map<String, Object>> selectAddressList(int memberNo);

	

}
