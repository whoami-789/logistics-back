package com.logistics.service;

import com.logistics.model.Orders;
import com.logistics.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Orders createOrder(Orders order) {
        return ordersRepository.save(order);
    }

    public Orders updateOrder(Long id, Orders updatedOrder) {
        Optional<Orders> existingOrder = ordersRepository.findById(id);
        if (existingOrder.isPresent()) {
            Orders order = existingOrder.get();
            order.setStartDate(updatedOrder.getStartDate());
            order.setEndDate(updatedOrder.getEndDate());
            order.setWeight(updatedOrder.getWeight());
            order.setPrice(updatedOrder.getPrice());
            order.setDistance(updatedOrder.getDistance());
            // обновляем остальные поля
            return ordersRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
    }

    public Orders getOrderById(Long id) {
        return ordersRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Order with ID " + id + " not found.")
        );
    }

    public void deleteOrder(Long id) {
        ordersRepository.deleteById(id);
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }


}
