package com.onlineshop.orderservice.service.serviceimpl;

import com.onlineshop.orderservice.rest.OnlineShopClient;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.repository.CartRepository;
import com.onlineshop.orderservice.repository.ItemsRepository;
import com.onlineshop.orderservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemsRepository itemRepo;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    OnlineShopClient onlineShopClient;

    public void deleteBId(long itemId, long cartId, long userId) {

        Optional<Cart> cart = cartRepo.findById(cartId);

        if (cart.isPresent()) {
            Set<Items> itemList = cart.get().getItem();
            Optional<Items> itemToRemove = itemList.stream().filter(e -> e.getItemId() == itemId).findFirst();

            if (itemToRemove.isPresent()) {

                itemList.remove(itemToRemove.get());

                cartRepo.save(cart.get());
            } else {
                throw new EntityNotFoundException("Item with id " + itemId + " not found in cart with id " + cartId);
            }
        } else {
            throw new EntityNotFoundException("Cart with id " + cartId + " not found");
        }

        Cart cart2 = cartRepo.findByOrderStatusAndUser(userId, "ACTIVE");

        Set<Items> item = itemRepo.findByCartCartId(cart2.getCartId());

        updateTotalPrice(item, cart2);

    }

    public Items updateCategory(RequestItems items, long id) {
        try {
            Items items1 = itemRepo.findById(id).get();
            items1.setQuantity(items.getQuantity());
            Product product = onlineShopClient.getProductById(items.getProductId());
            if (product == null) {
                throw new Exception("Product with this id was not found");
            }
            double total = product.getPrice() * items.getQuantity();
            items1.setTotalPrice(total);
            items1.setProduct(items.getProductId());
            Cart cart = cartRepo.findById(items1.getCart().getCartId()).get();
            Set<Items> itemList = itemRepo.findByCartCartId(cart.getCartId());
            updateTotalPrice(itemList, cart);
            return itemRepo.save(items1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTotalPrice(Set<Items> items, Cart cart) {
        double totalAmount = 0;
        for (Items itemCart : items) {
            totalAmount += itemCart.getTotalPrice();
        }
        cart.setTotalAmount(totalAmount);
        cartRepo.save(cart);
    }
}
