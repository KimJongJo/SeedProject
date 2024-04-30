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

}
