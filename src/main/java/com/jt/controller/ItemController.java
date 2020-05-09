package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;

@Controller   //返回页面
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private HttpClientService httpClientService;
	/**
	 * 根据商品信息，查询商品详情
	 */
	@RequestMapping("/{itemId}")
	public String findItemById(@PathVariable Long itemId,Model model) {
		
	
		//根据item的id查询数据
		Item item = itemService.findItemById(itemId);
		//将数据保存到域中
		model.addAttribute("item",item);
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}
	
}
