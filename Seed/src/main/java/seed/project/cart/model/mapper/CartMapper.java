package seed.project.cart.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import seed.project.cart.model.dto.Cart;

@Mapper
public interface CartMapper {

	/** 장바구니 리스트
	 * @return
	 */
	List<Cart> cartList(int memberNo);

	/** 장바구니 추가
	 * @param cartMap
	 * @return
	 */
	int addCart(Map<String, Integer> cartMap);

	/** 장바구니에 같은 씨앗이 존재한지 확인
	 * @param cartMap
	 * @return
	 */
	int checkSeed(Map<String, Integer> cartMap);

	/** 씨앗 개수 1 증가
	 * @param cartMap
	 */
	int seedUp(Map<String, Integer> cartMap);

}
