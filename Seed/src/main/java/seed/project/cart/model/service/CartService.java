package seed.project.cart.model.service;

import java.util.List;
import java.util.Map;

import seed.project.cart.model.dto.Cart;

public interface CartService {

	/** 장바구니 리스트
	 * @return
	 */
	List<Cart> cartList(int memberNo);

	/** 장바구니에 추가
	 * @param cartMap
	 * @return
	 */
	int addCart(Map<String, Integer> cartMap);

	/** 씨앗 수량 추가
	 * @param cartMap
	 * @return
	 */
	int seedPlus(Map<String, Object> cartMap);

	/** 씨앗 수량 감소
	 * @param cartMap
	 * @return
	 */
	int seedMinus(Map<String, Object> cartMap);

	/** 장바구니에서 삭제
	 * @param cartMap
	 * @return
	 */
	int cartDelete(Map<String, Object> cartMap);

}
