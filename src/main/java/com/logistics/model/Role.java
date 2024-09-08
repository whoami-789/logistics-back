package com.logistics.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="role")
@Data
@DynamicUpdate
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
