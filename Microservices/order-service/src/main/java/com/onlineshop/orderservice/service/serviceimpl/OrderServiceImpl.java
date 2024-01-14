package com.onlineshop.orderservice.service.serviceimpl;


import com.onlineshop.orderservice.rest.OnlineShopClient;
import com.onlineshop.orderservice.dto.*;
import com.onlineshop.orderservice.entity.*;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.model.RequestPaymentModel;
import com.onlineshop.orderservice.repository.*;
import com.onlineshop.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository OrderRepo;

    @Autowired
    OnlineShopClient onlineshopClient;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    ItemsRepository itemRepo;

    @Autowired
    SellerPaymentRepository sellerPaymentRepo;

    @Autowired
    PaymentsRepository paymentsRepo;

    public Orders placeOrder(RequestOrderPay requestOrder) {

        try {
            UUID oid = UUID.randomUUID();
            Orders orders = new Orders();
            Cart findByCart = cartRepo.findById(requestOrder.getCartId()).get();
            findByCart.setOrderStatus("Valid");
            orders.setCart(findByCart);
            User user = onlineshopClient.getUserById(requestOrder.getUserId());
            if (user == null) {
                throw new Exception("User with this id not found");
            }


            orders.setUser(requestOrder.getUserId());
            orders.setTotalAmount(findByCart.getTotalAmount());

            RequestOrderPay requestOrderPay = new RequestOrderPay();
            requestOrderPay.setCartId(requestOrder.getCartId());
            requestOrderPay.setUserId(requestOrder.getUserId());
            requestOrderPay.setPaymentOption(requestOrder.getPaymentOption());
            requestOrderPay.setTransactionId(requestOrder.getTransactionId());
            requestOrderPay.setPayerId(requestOrder.getPayerId());
            requestOrderPay.setPaymentStatus(requestOrder.getPaymentStatus());
            requestOrderPay.setDateTime(LocalDateTime.now());

            OrderPayment orderPayment = onlineshopClient.createOrderPayment(requestOrderPay);

            orders.setOrderPayment(orderPayment.getOrderPayId());


            List<SellerPayment> sellerPaymentList = new ArrayList<>();
            Set<Items> list = itemRepo.findByCartCartId(requestOrder.getCartId());
            for (Items item : list) {
                Product product = onlineshopClient.getProductById(item.getProduct());
                if (product == null) {
                    throw new Exception("Product with this id not found");
                }
                if (product.getStocks() >= item.getQuantity()) {
                    product.setStocks(product.getStocks() - item.getQuantity());
                }

                SellerPayment sellerPayments = new SellerPayment();
                sellerPayments.setOrderId(oid.toString());
                sellerPayments.setOrders(orders);
                sellerPayments.setBuyer(orders.getUser());
                sellerPayments.setIsPaymentDone("PENDING");
                sellerPayments.setItem(item);
                sellerPayments.setSeller(product.getSeller());
                sellerPayments.setTotalAmount(item.getTotalPrice());

                Payments payments = paymentsRepo.findBySeller(product.getSeller());

                if (payments == null) {
                    payments = new Payments();
                    payments.setSeller(product.getSeller());
                }
                double finalPendingPrice = payments.getPendingAmount() + item.getTotalPrice();
                double finalOverAllPrice = payments.getOverAllAmount() + item.getTotalPrice();
                payments.setPendingAmount(finalPendingPrice);
                payments.setOverAllAmount(finalOverAllPrice);
                paymentsRepo.save(payments);
                sellerPaymentList.add(sellerPayments);
            }
            sellerPaymentRepo.saveAll(sellerPaymentList);

            return OrderRepo.save(orders);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map<String, List<SellerPayment>> getAllOrdersBySeller(long userId) {
        List<SellerPayment> repo = sellerPaymentRepo.findAllBySellerOrderByOrdersDateTimeDesc(userId);

        return repo.stream().collect(Collectors.groupingBy(SellerPayment::getOrderId));
    }

    public Payments getAllPaymentsBySeller(long userId) {
        return paymentsRepo.findBySeller(userId);
    }

    public Payments sendPaymentsToSeller(RequestPaymentModel data) {
        Payments payments = paymentsRepo.findBySeller(data.getUserId());
        double amountPending = payments.getPendingAmount() - data.getAmount();
        payments.setPendingAmount(amountPending);
        return paymentsRepo.save(payments);
    }


    public List<OrderDto> getAllOrders(long userId) {
        List<OrderDto> orderList = new ArrayList<>();
        try {
            List<Orders> orders = OrderRepo.findAllByUserOrderByDateTimeDesc(userId);

            if (orders == null) {
                throw new Exception("Purchase a product");
            }

            for (Orders each : orders) {
                OrderDto dto = new OrderDto();
                dto.setDateTime(each.getDateTime());
                dto.setOrderId(each.getOrderId());
                dto.setOrderPayment(each.getOrderPayment());
                dto.setTotalAmount(each.getTotalAmount());
                dto.setUser(each.getUser());
                Cart crt = each.getCart();
                CartDto cart = new CartDto();
                cart.setCartId(crt.getCartId());
                cart.setOrderStatus(crt.getOrderStatus());
                cart.setTotalAmount(crt.getTotalAmount());
                cart.setUser(crt.getUser());
                Set<ItemsDto> itemSet = new HashSet<>();
                for (Items items : crt.getItem()) {
                    ItemsDto itm = new ItemsDto();
                    Product product = onlineshopClient.getProductById(items.getProduct());
                    itm.setItemId(items.getItemId());
                    itm.setQuantity(items.getQuantity());
                    itm.setTotalPrice(items.getTotalPrice());
                    itm.setProduct(product);
                    itemSet.add(itm);
                }
                cart.setItem(itemSet);
                dto.setCart(cart);
                orderList.add(dto);
            }

            return orderList;
        } catch (Exception e) {
            e.printStackTrace();
            return orderList;
        }


    }

    public List<Orders> getAllOrdersForAdmin() {
        List<Orders> orders = OrderRepo.findAllByOrderByDateTimeDesc();

        return orders;
    }
}
