package com.onlineshop.orderservice.service.serviceimpl;

import com.onlineshop.orderservice.rest.OnlineShopClient;
import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.dto.ItemsDto;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.repository.CartRepository;
import com.onlineshop.orderservice.repository.ItemsRepository;
import com.onlineshop.orderservice.response.ResponseCart;
import com.onlineshop.orderservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired

    CartRepository cartRepo;

    @Autowired
    ItemsRepository itemRepo;

    @Autowired
    OnlineShopClient onlineShopClient;

    public Cart saveCart(RequestItems requestItem) throws Exception {
        User user = onlineShopClient.getUserById(requestItem.getUserId());

        if (user == null) {
            throw new Exception("There is no user with this id in database");
        }

        Cart cart = cartRepo.findByOrderStatusAndUser(requestItem.getUserId(), "Valid");
        if (cart == null) {
            cart = new Cart();
            cart.setOrderStatus("Valid");
        }
        Product product = onlineShopClient.getProductById(requestItem.getProductId());
        if (product == null) {
            throw new Exception("There is no product with this value");
        }

        Items productId = itemRepo.findByProductAndCartCartId(requestItem.getProductId(), cart.getCartId());

        if (productId != null) {
            throw new Exception("Product already added to the cart");
        }

        Items item = new Items();
        item.setQuantity(requestItem.getQuantity());
        item.setProduct(requestItem.getProductId());
        item.setTotalPrice(product.getPrice() * requestItem.getQuantity());

        Set<Items> itemList = cart.getItem();
        item.setCart(cart);
        itemList.add(item);
        cart.setItem(itemList);

        double totalAmount = 0;
        for (Items itemCart : itemRepo.findByCartCartId(cart.getCartId())) {
            totalAmount += itemCart.getTotalPrice();
        }
        cart.setTotalAmount(totalAmount + product.getPrice() * requestItem.getQuantity());
        cart.setUser(requestItem.getUserId());
        return cartRepo.save(cart);
    }


    public CartDto getCartByUser(ResponseCart responseCart) {
        Cart cart = cartRepo.findByOrderStatusAndUser(responseCart.getUserId(), "Valid");
        if (cart != null) {
            CartDto dto = new CartDto();
            dto.setCartId(cart.getCartId());
            dto.setOrderStatus(cart.getOrderStatus());
            dto.setTotalAmount(cart.getTotalAmount());
            dto.setUser(cart.getUser());
            Set<ItemsDto> itemSet = new HashSet<>();
            for (Items items : cart.getItem()) {
                ItemsDto itm = new ItemsDto();
                Product product = onlineShopClient.getProductById(items.getProduct());
                itm.setItemId(items.getItemId());
                itm.setQuantity(items.getQuantity());
                itm.setTotalPrice(items.getTotalPrice());
                itm.setProduct(product);
                itemSet.add(itm);
            }
            dto.setItem(itemSet);
            return dto;
        }
        return null;
    }
}
