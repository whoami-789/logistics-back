package com.logistics.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverFilters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User driver;  // Водитель

    private Double minCargoWeight;
    private Double maxCargoWeight;
    private String cargoType;  // Тип груза
    private Integer price; // Стоимость заказа
    private Double distance; // Расстояние
    private Double length; // Длина
    private Double width;  // Ширина
    private Double height; // Высота
    private String currency; // Валюта
    private Boolean roundTrip; // Односторонний или туда-обратно
}
