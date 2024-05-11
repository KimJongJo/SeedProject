package seed.project.cart.controller;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import seed.project.cart.model.service.CartService;


@RestController
@RequiredArgsConstructor
@RequestMapping("cart")
public class CartController {

	private final CartService service;
	
	
	@PostMapping("addCart")
	public int addCart(@RequestBody Map<String, Integer> cartMap,
						Model model
			) {
		
		return service.addCart(cartMap);
	}
	
}
