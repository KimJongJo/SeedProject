package seed.project.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import seed.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 로그인 SQl 실행
	 * @param memberId
	 * @return loginMember
	 */
	Member login(String memberId);

	/** 이미 인증번호를 받았던 이메일의 인증번호를 교체
	 * @param map
	 * @return result
	 */
	int updateAuthKey(Map<String, String> map);

	/** 처음 인증번호를 받는 사람의 이메일로 인증번호 추가
	 * @param map
	 * @return result
	 */
	int insertAuthkey(Map<String, String> map);

	/** 이메일 인증번호 확인
	 * @param authString
	 * @return count
	 */
	int authCheck(String authString);
  
  
	/** 회원가입 - 아이디 중복 체크 SQL 실행
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

	/** 기존과 같은 비밀번호인지 확인
	 * @return brforeBc
	 */
	String beforeBc(String memberId);

	/** 아이디 찾기
	 * @param map
	 * @return result
	 */
	String findId(Map<String, String> map);

	/** 회원가입 - 이메일 중복체크
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	/** 회원가입 - 닉네임 중복체크
	 * @param memberNickname
	 * @return
	 */
	int checkNickname(String memberNickname);

	/** 회원가입(제출)
	 * @param inputMember
	 * @return
	 */
	int signup(Member inputMember);

	/** 카카오 로그인 회원 정보 가져오기
	 * @param kakaoId
	 * @param memberNickname
	 * @return
	 */
	int findIdForKakao(int kakaoId, String memberNickname);

	/** 카카오 로그인
	 * @param memberId
	 * @return
	 */
	Member loginForKakao(int memberId);

	/** 카카오 회원가입
	 * @param signMember
	 * @return
	 */
	int signForKakao(Member signMember);


}
