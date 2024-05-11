package seed.project.cart.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import seed.project.cart.model.service.CartService;

@Controller
@RequiredArgsConstructor
public class CartController {

	private final CartService service;
	
}
