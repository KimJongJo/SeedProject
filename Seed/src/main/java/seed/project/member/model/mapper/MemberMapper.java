package seed.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import seed.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 로그인 SQl 실행
	 * @param memberId
	 * @return loginMember
	 */
	Member login(String memberId);

	/** 회원가입 - 아이디 중복 체크 SQL 실행
	 * @param memberId
	 * @return
	 */
	int checkId(String memberId);

}
