package seed.project.Manager.mapper;

import java.util.List;

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

}
