package com.logistics.service;

import com.logistics.model.Status;
import com.logistics.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

//    public Status updateStatus(Long id, Status statusDetails) {
//        Status status = statusRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Status not found for id: " + id));
//
//        status.setName(statusDetails.getName());
//        status.setDescription(statusDetails.getDescription());
//
//        return statusRepository.save(status);
//    }

    public Status getStatusById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Status not found for id: " + id));
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }
}
