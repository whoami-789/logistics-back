package com.logistics.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
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

    public DriverFilters() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DriverFilters;
    }

}
