package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.objectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService {
	
	

	/**
	 * 问题：在jt-web
	 */
	@Autowired
	private HttpClientService httpClientService;
	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com:80/web/item/findItemById/"+itemId;
		String json = httpClientService.doGet(url);
		return objectMapperUtil.toObject(json, Item.class);
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com:80/web/item/findItemDescById/"+itemId;
		String json = httpClientService.doGet(url);
		return objectMapperUtil.toObject(json, ItemDesc.class);
	}


	
}
