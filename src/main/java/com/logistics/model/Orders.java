package com.logistics.model;

import com.logistics.model.enums.CustomerStatus;
import com.logistics.model.enums.DriverStatus;
import com.logistics.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String telegram;
    private String pnumber;

    private Integer weight;
    private Double distance;
    private String cargoType; // Тип груза
    private String carType; // Тип кузова
    private Integer min;
    private Integer max;
    private String tripType;
    private String adr;

    private Double length;
    private Double width;
    private Double height;

    private String paymentMethod;
    private Integer advancePaymentPercentage;
    private String advancePaymentMethod;
    private String currency;
    private Integer price;
    private String nds;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private DriverStatus driverStatus;

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes;

    @Transient
    private Long customerNum; // Поле для ID, передаваемого из фронта

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private User executor;

    private String carBody;
    private String workType;

    // Добавляем связь с фильтрами водителя
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_filters_id")
    private DriverFilters driverFilters;

    public Orders() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Orders;
    }

    public void updateStatus() {
        // Пример логики на enum (более чистый вариант)
        if (driverStatus == DriverStatus.CANCELLED) {
            // Водитель отменил -> освобождаем заказ
            this.status = OrderStatus.CREATED;
            this.executor = null;
            return;
        }

        if (customerStatus == CustomerStatus.CANCELLED) {
            // Заказчик отменил -> заказ полностью отменён
            this.status = OrderStatus.CANCELLED;
            return;
        }

        if (driverStatus == DriverStatus.DELIVERED && customerStatus == CustomerStatus.DELIVERED) {
            this.status = OrderStatus.COMPLETED;
            return;
        }

        if (driverStatus == DriverStatus.BOOKED && customerStatus == CustomerStatus.BOOKED) {
            this.status = OrderStatus.IN_PROGRESS;
            return;
        }

        // Если хотя бы одна сторона BOOKED, но не обе:
        if (driverStatus == DriverStatus.BOOKED || customerStatus == CustomerStatus.BOOKED) {
            this.status = OrderStatus.WAITING_CONFIRMATION;
            return;
        }

        // По умолчанию (если ничего не подошло)
        this.status = OrderStatus.CREATED;
    }

    // setDriverStatus и setCustomerStatus, вызывающие updateStatus()
    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
        updateStatus();
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
        updateStatus();
    }

}


