package seed.project.Manager.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import seed.project.Manager.model.dto.Seed;

public interface SeedService {

	/** 상품 등록
	 * @param seed
	 * @return
	 */
	int seedAdd(Seed seed);

	/** 씨앗 리스트
	 * @return
	 */
	List<Seed> seedList();

}
