package com.logistics.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer weight; // Вес груза в тоннах
    private Integer price; // Стоимость заказа
    private Double distance; // Расстояние

    private String cargoType; // Тип груза (габариты, коробки, навалом)

    // Габариты груза (если выбраны габариты)
    private Double length; // Длина
    private Double width;  // Ширина
    private Double height; // Высота

    private String paymentMethod; // Способ оплаты (наличные или перечисление)
    private Integer advancePaymentPercentage; // Процент авансового платежа
    private String advancePaymentMethod; // Метод аванса (наличные или перечисление)
    private String currency; // Валюта (USD, RUB и т.д.)

    private Boolean roundTrip; // Односторонний заказ или туда-обратно

    // Список маршрутов для многостоповой поездки
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Route> routes;

    // Заказчик
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    // Исполнитель
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private User executor;

    // Тип кузова
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carBody_id")
    private CarBody carBody;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workType_id")
    private WorkType workType;

    // Добавляем связь с фильтрами водителя
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_filters_id")
    private DriverFilters driverFilters;
}

