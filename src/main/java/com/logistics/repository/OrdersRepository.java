package com.logistics.repository;

import com.logistics.dto.AvailableOrderDTO;
import com.logistics.model.DriverFilters;
import com.logistics.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // Поиск всех заказов, где пользователь — заказчик
    List<Orders> findAllByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM orders WHERE customer_id = :customerId AND status <> :status", nativeQuery = true)
    List<Orders> findAllByCustomerIdAndStatusIsNot(@Param("customerId") Long customerId, @Param("status") String status);

    @Query(value = "SELECT * FROM orders WHERE status = :status", nativeQuery = true)
    List<Orders> findAllByStatusIsNot(@Param("status") String status);

    // Поиск всех заказов, где пользователь — исполнитель
    List<Orders> findAllByExecutorId(Long executorId);

    // Поиск заказа по ID и ID заказчика
    Optional<Orders> findByIdAndCustomerId(Long orderId, Long customerId);

    // Поиск заказа по ID и ID исполнителя
    Optional<Orders> findByIdAndExecutorId(Long orderId, Long executorId);

    List<Orders> findOrdersByDriverFilters(DriverFilters filters);

    @Query("SELECT COUNT(o) FROM Orders o")
    int countAvailableOrdersForUser();

    @Query("SELECT new com.logistics.dto.AvailableOrderDTO(r.countryFrom, COUNT(o)) " +
            "FROM Orders o JOIN o.routes r " +
            "WHERE o.executor IS NULL AND o.customer.id <> :userId " +
            "GROUP BY r.countryFrom")
    List<AvailableOrderDTO> findAvailableOrdersByCountry(Long userId);

}
