package com.logistics.controller;
import com.logistics.model.CarBody;
import com.logistics.service.CarBodyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/carbodies")
public class CarBodyController {

    private final CarBodyService carBodyService;

    public CarBodyController(CarBodyService carBodyService) {
        this.carBodyService = carBodyService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarBody> createCarBody(@RequestBody CarBody carBody) {
        CarBody createdCarBody = carBodyService.createCarBody(carBody);
        return ResponseEntity.ok(createdCarBody);
    }

//    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<CarBody> updateCarBody(@PathVariable Long id, @RequestBody CarBody carBody) {
//        CarBody updatedCarBody = carBodyService.updateCarBody(id, carBody);
//        return ResponseEntity.ok(updatedCarBody);
//    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarBody> getCarBodyById(@PathVariable Long id) {
        CarBody carBody = carBodyService.getCarBodyById(id);
        return ResponseEntity.ok(carBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarBody(@PathVariable Long id) {
        carBodyService.deleteCarBody(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CarBody>> getAllCarBodies() {
        List<CarBody> carBodies = carBodyService.getAllCarBodies();
        return ResponseEntity.ok(carBodies);
    }
}
