package com.logistics.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "car_body")
public class CarBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public CarBody(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public CarBody() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CarBody;
    }

}
