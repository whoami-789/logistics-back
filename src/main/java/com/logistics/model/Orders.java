package com.logistics.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
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


    private String status;
    private String driverStatus;
    private String customerStatus;

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

    public Orders(Long id, LocalDate startDate, LocalDate endDate, Integer weight, Integer price, Double distance, String cargoType, Double length, Double width, Double height, String paymentMethod, Integer advancePaymentPercentage, String advancePaymentMethod, String currency, String status, Boolean roundTrip, List<Route> routes, Long customerNum, User customer, User executor, String carBody, String workType, DriverFilters driverFilters) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weight = weight;
        this.price = price;
        this.distance = distance;
        this.cargoType = cargoType;
        this.length = length;
        this.width = width;
        this.height = height;
        this.paymentMethod = paymentMethod;
        this.advancePaymentPercentage = advancePaymentPercentage;
        this.advancePaymentMethod = advancePaymentMethod;
        this.currency = currency;
        this.status = status;
        this.routes = routes;
        this.customerNum = customerNum;
        this.customer = customer;
        this.executor = executor;
        this.carBody = carBody;
        this.workType = workType;
        this.driverFilters = driverFilters;
    }

    public Orders() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Orders;
    }

    public void updateStatus() {
        if (driverStatus != null && driverStatus.equals(customerStatus)) {
            if (driverStatus.equals("Забронирован")) {
                this.status = "В пути";  // Если оба статуса "Забронирован", основной статус "В пути"
            } else if (driverStatus.equals("Доставлен")) {
                this.status = "Завершен";  // Если оба статуса "Доставлен", основной статус "Завершен"
            } else {
                this.status = driverStatus;  // В остальных случаях основной статус просто совпадает с обоими статусами
            }
        } else {
            this.status = "Ожидает подтверждения";  // Или любое другое значение по умолчанию, если статусы разные
        }
    }


    // Сеттер для driverStatus с обновлением основного статуса
    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
        updateStatus();
    }

    // Сеттер для customerStatus с обновлением основного статуса
    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
        updateStatus();
    }

}


