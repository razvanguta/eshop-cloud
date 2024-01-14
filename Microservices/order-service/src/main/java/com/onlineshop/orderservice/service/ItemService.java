package com.onlineshop.orderservice.service;

 

import org.springframework.stereotype.Service;

import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;

@Service
public interface ItemService {
	void deleteBId(long itemId, long cartId, long userId);

	 Items updateCategory(RequestItems items, long id);
}
