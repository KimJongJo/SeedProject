package seed.project.member.model.service;


import java.util.Map;

import seed.project.member.model.dto.Member;





public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 이메일 인증번호 발급
	 * @param memberEmail
	 * @return result
	 */
	String sendEmail(String html, String memberEmail);

	/** 이메일 인증번호 확인
	 * @param authString
	 * @return result
	 */
	int authCheck(String authString);
  
	/** 회원가입 - 아이디 중복 체크
	 * @param memberId
	 * @return
	 */
	int checkId(String memberId);

	/** 존재하는 회원인지 조회(아이디, 이메일)
	 * @param member
	 * @return result
	 */
	int findResult(Member member);

	/** 비밀번호 찾기 -> 변경
	 * @param map
	 * @return result
	 */
	int findPwCh(Map<String, String> map);

	/** 아이디 찾기
	 * @param map
	 * @return result
	 */
	int findId(Map<String, String> map);
	
	
}
