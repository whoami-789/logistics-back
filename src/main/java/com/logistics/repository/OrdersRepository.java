package com.logistics.repository;

import com.logistics.model.DriverFilters;
import com.logistics.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // Поиск всех заказов, где пользователь — заказчик
    List<Orders> findAllByCustomerId(Long customerId);

    // Поиск всех заказов, где пользователь — исполнитель
    List<Orders> findAllByExecutorId(Long executorId);

    // Поиск заказа по ID и ID заказчика
    Optional<Orders> findByIdAndCustomerId(Long orderId, Long customerId);

    // Поиск заказа по ID и ID исполнителя
    Optional<Orders> findByIdAndExecutorId(Long orderId, Long executorId);

    List<Orders> findOrdersByDriverFilters(DriverFilters filters);
}
