package seed.project.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import seed.project.board.model.dto.Comment;
import seed.project.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	/** 회원 정보 수정
	 * @param inputMember
	 * @return
	 */
	int updateInfo(Member inputMember);

	/** 비번 변경을 위한 현재 비번 검색
	 * @param memberNo
	 * @return
	 */
	String selectPw(int memberNo);

	/** 회원 비밀번호 변경
	 * @param paramMap
	 * @return
	 */
	int updatePw(Map<String, Object> paramMap);



	/** 회원 주소 검색
	 * @return
	 */
	List<Map<String, Object>> selectAddressList(int memberNo);

	/** 주소 추가하기전 저장된 주소 개수 조회
	 * @param memberNo 
	 * @return
	 */
	int addressCount(int memberNo);
	
	
	/** 회원 탈퇴
	 * @param memberNo
	 * @return result
	 */
	int deleteMember(int memberNo);

	/** 회원 주소 검색
	 * @param memberNo
	 * @return
	 */
	String addressSelect(int memberNo);


	/** 회원 주소 변경
	 * @param addressMap
	 * @return
	 */
	int addressUpdate(Map<String, Object> addressMap);

	/** 마이페이지 - 작성한 댓글 수 조회(삭제되지 않은)
	 * @param memberNo
	 * @return
	 */
	int getCommentListCount(int memberNo);

	
	/** 마이페이지 - 작성한 댓글 목록 조회
	 * @param memberNo
	 * @param rowBounds
	 * @return
	 */
	List<Comment> selectCommentList(int memberNo, RowBounds rowBounds);

	
	

}
