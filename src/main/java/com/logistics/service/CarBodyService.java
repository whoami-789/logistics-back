package com.logistics.service;

import com.logistics.model.CarBody;
import com.logistics.repository.CarBodyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarBodyService {
    private final CarBodyRepository carBodyRepository;

    public CarBodyService(CarBodyRepository carBodyRepository) {
        this.carBodyRepository = carBodyRepository;
    }

    public CarBody createCarBody(CarBody carBody) {
        return carBodyRepository.save(carBody);
    }

//    public CarBody updateCarBody(Long id, CarBody updatedCarBody) {
//        Optional<CarBody> existingCarBody = carBodyRepository.findById(id);
//        if (existingCarBody.isPresent()) {
//            CarBody carBody = existingCarBody.get();
//            carBody.setName(updatedCarBody.getName());
//            return carBodyRepository.save(carBody);
//        } else {
//            throw new IllegalArgumentException("CarBody with ID " + id + " not found.");
//        }
//    }

    public CarBody getCarBodyById(Long id) {
        return carBodyRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("CarBody with ID " + id + " not found.")
        );
    }

    public void deleteCarBody(Long id) {
        carBodyRepository.deleteById(id);
    }

    public List<CarBody> getAllCarBodies() {
        return carBodyRepository.findAll();
    }
}
