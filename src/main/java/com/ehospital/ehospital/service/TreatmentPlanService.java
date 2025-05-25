package com.ehospital.ehospital.service;

import com.ehospital.ehospital.model.TreatmentPlan;
import com.ehospital.ehospital.repository.TreatmentPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentPlanService {

    private final TreatmentPlanRepository treatmentPlanRepository;

    public TreatmentPlanService(TreatmentPlanRepository treatmentPlanRepository) {
        this.treatmentPlanRepository = treatmentPlanRepository;
    }

    public void saveTreatmentPlan(TreatmentPlan treatmentPlan) {
        treatmentPlanRepository.save(treatmentPlan);
    }

    public List<TreatmentPlan> getAllTreatmentPlans() {
        return treatmentPlanRepository.findAll();
    }
}
