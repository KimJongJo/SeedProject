package seed.project.cart.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seed.project.cart.model.dto.Cart;
import seed.project.cart.model.mapper.CartMapper;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

	private final CartMapper mapper;

	
	// 장바구니 리스트
	@Override
	public List<Cart> cartList(int memberNo) {
		
		return mapper.cartList(memberNo);
	}


	// 장바구니 추가
	@Override
	public int addCart(Map<String, Integer> cartMap) {
		
		// 기존에 같은 씨앗을 추가한게 있는지 확인
		int result = mapper.checkSeed(cartMap);
		
		// 같은 씨앗이 존재하면 update -> 수량 1 증가
		if(result > 0) {
			return mapper.seedUp(cartMap);
			
		// 존재하지 않으면 insert -> 씨앗 추가
		}else {
			return mapper.addCart(cartMap);
		}
		
		
	}
	
}
