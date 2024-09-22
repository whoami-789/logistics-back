package com.logistics.service;
import com.logistics.model.WorkType;
import com.logistics.repository.WorkTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTypeService {

    private final WorkTypeRepository workTypeRepository;

    public WorkTypeService(WorkTypeRepository workTypeRepository) {
        this.workTypeRepository = workTypeRepository;
    }

    public WorkType createWorkType(WorkType workType) {
        return workTypeRepository.save(workType);
    }
//
//    public WorkType updateWorkType(Long id, WorkType workTypeDetails) {
//        WorkType workType = workTypeRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("WorkType not found for id: " + id));
//
//        workType.setType(workTypeDetails.getType());
//        workType.setDescription(workTypeDetails.getDescription());
//
//        return workTypeRepository.save(workType);
//    }

    public WorkType getWorkTypeById(Long id) {
        return workTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("WorkType not found for id: " + id));
    }

    public void deleteWorkType(Long id) {
        workTypeRepository.deleteById(id);
    }

    public List<WorkType> getAllWorkTypes() {
        return workTypeRepository.findAll();
    }
}
