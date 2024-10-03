package com.logistics.controller;

import com.logistics.model.Orders;
import com.logistics.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
        // Логика фильтрации для габаритов
        if ("габариты".equals(order.getCargoType())) {
            if (order.getLength() == null || order.getWidth() == null || order.getHeight() == null) {
                throw new IllegalArgumentException("Для габаритного груза необходимо указать длину, ширину и высоту");
            }
        }

        // Логика фильтрации для оплаты
        if (order.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Необходимо указать способ оплаты (наличные или перечисление)");
        }

        if (order.getAdvancePaymentPercentage() != null && order.getAdvancePaymentMethod() == null) {
            throw new IllegalArgumentException("Если указан аванс, необходимо указать способ его оплаты (наличные или перечисление)");
        }

        // Если всё ок, сохраняем заказ
        Orders createdOrder = ordersService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Обновление заказа по ID
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders order) {
        Orders updatedOrder = ordersService.updateOrder(id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    // Бронирование заказа водителем
    @PutMapping("/book/{orderId}")
    public ResponseEntity<Orders> bookOrder(
            @PathVariable Long orderId,
            @RequestParam Long driverId) {

        Orders bookedOrder = ordersService.bookOrder(orderId, driverId);
        return ResponseEntity.ok(bookedOrder);
    }

    // Получение всех заказов для заказчика
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getCustomerOrders(@PathVariable Long customerId) {
        List<Orders> orders = ordersService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    // Получение всех заказов для исполнителя
    @GetMapping("/executor/{executorId}")
    public ResponseEntity<List<Orders>> getExecutorOrders(@PathVariable Long executorId) {
        List<Orders> orders = ordersService.getOrdersByExecutor(executorId);
        return ResponseEntity.ok(orders);
    }

    // Получение детальной информации о заказе для заказчика
    @GetMapping("/{orderId}/customer/{customerId}")
    public ResponseEntity<Orders> getOrderDetailsForCustomer(@PathVariable Long orderId, @PathVariable Long customerId) {
        return ordersService.getOrderDetailsForCustomer(customerId, orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Получение детальной информации о заказе для исполнителя
    @GetMapping("/{orderId}/executor/{executorId}")
    public ResponseEntity<Orders> getOrderDetailsForExecutor(@PathVariable Long orderId, @PathVariable Long executorId) {
        return ordersService.getOrderDetailsForExecutor(executorId, orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Получение всех заказов для администратора
    @GetMapping("/admin")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Удаление заказа
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        try {
            ordersService.deleteOrder(orderId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}