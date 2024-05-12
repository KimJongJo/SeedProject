package seed.project.Manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import seed.project.Manager.model.dto.Seed;

@Mapper
public interface SeedMapper {

	/** 씨앗 등록
	 * @param seed
	 * @return
	 */
	int addSeed(Seed seed);

	/** 씨앗 리스트
	 * @return
	 */
	List<Seed> seedList();

	/** 씨앗 가격
	 * @param seedName
	 * @return
	 */
	String seedPrice(String seedName);

	/** 씨앗 가격 수정
	 * @param seedMap
	 * @return
	 */
	int seedUpdatrPrice(Map<String, String> seedMap);

	/** 씨앗 품절 체크
	 * @param seedName
	 * @return
	 */
	int seedDeleteOnCheck(String seedName);

	/** 씨앗 품절
	 * @param seedName
	 * @return
	 */
	int seedDeleteOn(String seedName);

	/** 씨앗 판매중인지 체크
	 * @param seedName
	 * @return
	 */
	int seedDeleteOffCheck(String seedName);

	/** 씨앗 품절 해제
	 * @param seedName
	 * @return
	 */
	int seedDeleteOff(String seedName);

}
