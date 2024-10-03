package com.logistics.repository;

import com.logistics.model.DriverFilters;
import com.logistics.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverFiltersRepository extends JpaRepository<DriverFilters, Long> {
    DriverFilters findByDriverId(Long driverId); // Метод для поиска фильтров по водителю
}
