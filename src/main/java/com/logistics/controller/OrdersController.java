package com.logistics.controller;

import com.logistics.dto.OrdersDTO;
import com.logistics.dto.UserStatisticsDTO;
import com.logistics.model.Orders;
import com.logistics.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private final OrdersService ordersService;
    private final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    // Получение всех ордеров
    @GetMapping
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        List<OrdersDTO> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrdersDTO> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrdersDTO updatedOrderDTO) {
        logger.info("Updating order with ID: {}", orderId);

        // Вызов сервиса для обновления заказа
        OrdersDTO updatedOrder = ordersService.updateOrder(orderId, updatedOrderDTO);

        return ResponseEntity.ok(updatedOrder);
    }


    // Получение ордеров, где заказчик не совпадает с переданным userId
    @GetMapping("/exclude-customer/{customerId}")
    public ResponseEntity<List<OrdersDTO>> getOrdersExcludingCustomer(@PathVariable Long customerId) {
        List<OrdersDTO> orders = ordersService.getAllOrdersExcludingCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrdersDTO> createOrder(@RequestBody OrdersDTO orderDTO) {
        logger.info("Received order: {}", orderDTO);

        // Проверка на валидность полей
        validateOrder(orderDTO);

        // Сохраняем заказ
        OrdersDTO createdOrder = ordersService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    private void validateOrder(OrdersDTO orderDTO) {
        if ("габариты".equals(orderDTO.getCargoType())) {
            if (orderDTO.getLength() == null || orderDTO.getWidth() == null || orderDTO.getHeight() == null) {
                throw new IllegalArgumentException("Для габаритного груза необходимо указать длину, ширину и высоту");
            }
        }

        if (orderDTO.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Необходимо указать способ оплаты (наличные или перечисление)");
        }

        if (orderDTO.getAdvancePaymentPercentage() != null && orderDTO.getAdvancePaymentMethod() == null) {
            throw new IllegalArgumentException("Если указан аванс, необходимо указать способ его оплаты (наличные или перечисление)");
        }
    }

    // Бронирование заказа водителем
    @PutMapping("/book/{orderId}")
    public ResponseEntity<OrdersDTO> bookOrder(
            @PathVariable Long orderId,
            @RequestParam Long driverId) {

        OrdersDTO bookedOrder = ordersService.bookOrder(orderId, driverId);
        return ResponseEntity.ok(bookedOrder);
    }

    @PutMapping("/status/customer/{orderId}")
    public ResponseEntity<OrdersDTO> changeCustomerStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        OrdersDTO bookedOrder = ordersService.customerStatus(orderId, status);
        return ResponseEntity.ok(bookedOrder);
    }

    @PutMapping("/status/driver/{orderId}")
    public ResponseEntity<OrdersDTO> changeDriverStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            @RequestParam Long driverId) {

        OrdersDTO bookedOrder = ordersService.driverStatus(orderId, status, driverId);
        return ResponseEntity.ok(bookedOrder);
    }


    // Получение всех заказов для заказчика
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrdersDTO>> getCustomerOrders(@PathVariable Long customerId) {
        List<OrdersDTO> orders = ordersService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/unbooked/{customerId}")
    public ResponseEntity<List<OrdersDTO>> getCustomerUnbookedOrders(@PathVariable Long customerId) {
        List<OrdersDTO> orders = ordersService.getUnbookedOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/active/{customerId}")
    public ResponseEntity<List<OrdersDTO>> getCustomerActiveOrders(@PathVariable Long customerId) {
        List<OrdersDTO> orders = ordersService.getActiveOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/active")
    public ResponseEntity<List<OrdersDTO>> getActiveOrders() {
        List<OrdersDTO> orders = ordersService.getActiveOrders();
        return ResponseEntity.ok(orders);
    }

    // Получение всех заказов для исполнителя
    @GetMapping("/executor/{executorId}")
    public ResponseEntity<List<OrdersDTO>> getExecutorOrders(@PathVariable Long executorId) {
        List<OrdersDTO> orders = ordersService.getOrdersByExecutor(executorId);
        return ResponseEntity.ok(orders);
    }

    // Получение детальной информации о заказе для заказчика
    @GetMapping("/{orderId}/customer/{customerId}")
    public ResponseEntity<OrdersDTO> getOrderDetailsForCustomer(@PathVariable Long orderId, @PathVariable Long customerId) {
        return ordersService.getOrderDetailsForCustomer(customerId, orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Получение детальной информации о заказе для исполнителя
    @GetMapping("/{orderId}/executor/{executorId}")
    public ResponseEntity<OrdersDTO> getOrderDetailsForExecutor(@PathVariable Long orderId, @PathVariable Long executorId) {
        return ordersService.getOrderDetailsForExecutor(executorId, orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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

    @GetMapping("/user/statistics/{userId}")
    public ResponseEntity<UserStatisticsDTO> getUserStatistics(@PathVariable Long userId) {
        UserStatisticsDTO statistics = ordersService.getUserStatistics(userId);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<OrdersDTO>> filterOrders(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) String carBody,
            @RequestParam(required = false) Integer minWeight,
            @RequestParam(required = false) Integer maxWeight,
            @RequestParam(required = false) Integer priceFrom,
            @RequestParam(required = false) Integer priceTo,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) String countryFrom,
            @RequestParam(required = false) String cityFrom,
            @RequestParam(required = false) String countryTo,
            @RequestParam(required = false) String cityTo,
            @RequestParam(required = false) Long userId
    ) {
        // Вызываем сервис, чтобы получить список Orders (сущностей), который будет отфильтрован
        List<OrdersDTO> filteredOrders = ordersService.filterOrders(
                startDate, carBody, minWeight, maxWeight, priceFrom, priceTo, currency, countryFrom, cityFrom, countryTo, cityTo, userId
        );
        return ResponseEntity.ok(filteredOrders);
    }

}
