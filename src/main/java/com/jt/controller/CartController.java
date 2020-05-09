package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(check=false)
	private DubboCartService cartService;
	/**
	 * 当用户点击购物车按钮时，需要跳转到用户的购物车界面
	 * cart.jsp
	 * u'r'l
	 */
	
	
	
	@RequestMapping("/show")
	public String show(Model model) {
		Long userId = 7L;
		List<Cart> cartList = cartService.findCartById(userId); 
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/update/num/{ItemId}/{num}")
	public SysResult updateNum(Cart cart) {
		Long userId = 7L;
		cart.setUserId(userId);
		cartService.update(cart);
		
		return SysResult.success();
	}
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId) {
		Long userId = 7L;
		Cart cart = new Cart();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
		Long userId = 7L;
		cart.setUserId(userId);
		cartService.saceCart(cart);
		return "redirect:/cart/show.html";
		
	}
	
}
