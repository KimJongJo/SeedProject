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

}
