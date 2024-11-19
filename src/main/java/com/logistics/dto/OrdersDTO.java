package com.logistics.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer weight;
    private Integer price;
    private Double distance;
    private String cargoType; // Тип груза
    private Double length;
    private Double width;
    private Double height;
    private String paymentMethod;
    private Integer advancePaymentPercentage;
    private String advancePaymentMethod;
    private String currency;
    private String status;
    private String customerStatus;
    private String driverStatus;
    private Boolean roundTrip;
    private List<RouteDTO> routes; // Если у вас есть DTO для маршрутов
    private Long customerNum; // Поле для ID, передаваемого из фронта
    private Long customer_id; // ID заказчика
    private Long executorId; // ID исполнителя
    private String carBody; // Убедитесь, что это поле есть
    private String workType;
    private Long driverFiltersId; // ID фильтров водителя
    private String carType;
    private String min;
    private String max;
    private String adr;
    private String nds;
    private String telegram;
    private String pnumber;
    private String tripType;

}
