package com.logistics.repository;

import com.logistics.model.CarBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBodyRepository extends JpaRepository<CarBody, Long> {
}
