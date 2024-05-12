package seed.project.cart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import seed.project.cart.model.dto.Cart;
import seed.project.cart.model.service.CartService;


@RestController
@RequiredArgsConstructor
@RequestMapping("cart")
public class CartController {

	private final CartService service;
	
	 
	/** 쇼핑몰에서 장바구니에 담기
	 * @param cartMap
	 * @param model
	 * @return
	 */
	@PostMapping("addCart")
	public int addCart(@RequestBody Map<String, Integer> cartMap,
						Model model
			) {
		
		return service.addCart(cartMap);
	}
	
	
	/** 장바구니에서 수량 추가
	 * @param cartMap
	 * @return
	 */
	@PutMapping("seedPlus")
	public int seedPlus(@RequestBody Map<String, Object> cartMap) {
		
		return service.seedPlus(cartMap);
	}
	
	
	/** 장바구니에서 수량 감소
	 * @param cartMap
	 * @return
	 */
	@PutMapping("seedMinus")
	public int seedMinus(@RequestBody Map<String, Object> cartMap) {
		
		return service.seedMinus(cartMap);
	}
	
	
	/** 장바구니에서 삭제
	 * @param cartMap
	 * @return
	 */
	@DeleteMapping("cartDelete")
	public int cartDelete(@RequestBody Map<String, Object> cartMap) {
		
		return service.cartDelete(cartMap);
	}
	
	
	/** 장바구니 목록 불러오기
	 * @param memberNo
	 * @return
	 */
	@GetMapping("basket")
	public List<Cart> cartList(@RequestParam("memberNo") int memberNo) {
		
		return service.cartList(memberNo);
	}
	
	
	@PostMapping("seedAdd")
	public int seedAdd(@RequestBody Map<String, Integer> cartMap) {
		
		return service.seedAdd(cartMap);
	}
	
}
