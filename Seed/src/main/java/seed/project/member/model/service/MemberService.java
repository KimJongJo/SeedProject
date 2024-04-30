package seed.project.member.model.service;

import seed.project.member.model.dto.Member;





public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 회원가입 - 아이디 중복 체크
	 * @param memberId
	 * @return
	 */
	int checkId(String memberId);
	
	
}
