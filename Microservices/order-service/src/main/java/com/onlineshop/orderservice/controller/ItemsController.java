package com.onlineshop.orderservice.controller;


import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/items")
public class ItemsController {

    @Autowired
    ItemService itemService;

    @DeleteMapping("/{itemId}/{cartid}/{userId}")
    public ResponseEntity<?> deleteItemById(@PathVariable("itemId") long itemId, @PathVariable("cartid")
    long cartId, @PathVariable("userId") long userId) {
        itemService.deleteBId(itemId, cartId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItems(@PathVariable("id") long id, @RequestBody RequestItems item) {
        itemService.updateCategory(item, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
