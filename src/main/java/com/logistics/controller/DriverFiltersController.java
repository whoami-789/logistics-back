package com.logistics.controller;

import com.logistics.model.DriverFilters;
import com.logistics.service.DriverFiltersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver-filters")
public class DriverFiltersController {

    private final DriverFiltersService driverFiltersService;

    public DriverFiltersController(DriverFiltersService driverFiltersService) {
        this.driverFiltersService = driverFiltersService;
    }

    @PostMapping
    public ResponseEntity<DriverFilters> createOrUpdateFilters(@RequestBody DriverFilters filters) {
        DriverFilters savedFilters = driverFiltersService.saveDriverFilters(filters);
        return new ResponseEntity<>(savedFilters, HttpStatus.CREATED);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverFilters> getFilters(@PathVariable Long driverId) {
        DriverFilters filters = driverFiltersService.getDriverFilters(driverId);
        return ResponseEntity.ok(filters);
    }
}
