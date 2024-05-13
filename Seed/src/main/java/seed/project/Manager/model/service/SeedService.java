package seed.project.Manager.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import seed.project.Manager.model.dto.Seed;

public interface SeedService {

	/** 상품 등록
	 * @param seed
	 * @return
	 * @throws IOException 
	 */
	int seedAdd(Seed seed) throws IOException;

	/** 씨앗 리스트
	 * @return
	 */
	List<Seed> seedList();

	/** 씨앗 가격 불러오기
	 * @param seedName
	 * @return
	 */
	String seedPrice(String seedName);

	/** 씨앗 가격 수정
	 * @param seedMap
	 * @return
	 */
	int seedUpdatrPrice(Map<String, String> seedMap);

	/** 씨앗 품절 
	 * @param seedName
	 * @return
	 */
	int seedDeleteOn(String seedName);

	/** 씨앗 품절 해제
	 * @param seedName
	 * @return
	 */
	int seedDeleteOff(String seedName);

	/** 씨앗 정보
	 * @param seedNo
	 * @return
	 */
	Seed seedDetail(int seedNo);

	/** 씨앗 정렬
	 * @param sortType
	 * @return
	 */
	List<Seed> seedSort(int sortType);

	/** 씨앗 검색
	 * @param key
	 * @return
	 */
	List<Seed> seedSearch(String key);




}
