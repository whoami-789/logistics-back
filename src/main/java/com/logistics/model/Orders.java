package com.logistics.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer weight;
    private Integer price;
    private Double distance;

    // Флаг для определения типа заказа: односторонний или туда-обратно
    private Boolean roundTrip; // Если true, заказ туда и обратно

    // Размеры груза (если выбраны габариты)
    private Double length;  // Длина
    private Double width;   // Ширина
    private Double height;  // Высота

    // Детали оплаты
    private String paymentMethod; // 'наличные' или 'перечисление'
    private Integer advancePaymentPercentage; // % аванса
    private String currency; // Валюта, например 'USD'

    // Заказчик
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;

    // Исполнитель
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private User executor;

    // Статус заказа
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    // Тип кузова
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carBody_id", referencedColumnName = "id")
    private CarBody carBody;

    // Тип работы (односторонняя поездка, многостоповая поездка)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workType_id", referencedColumnName = "id")
    private WorkType workType;

    // Список маршрутов для многоточечных заказов
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Route> routes;
}
