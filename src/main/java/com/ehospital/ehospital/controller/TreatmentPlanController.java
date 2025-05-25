package com.ehospital.ehospital.controller;

import com.ehospital.ehospital.model.ClinicalFile;
import com.ehospital.ehospital.model.TreatmentPlan;
import com.ehospital.ehospital.repository.ClinicalFileRepository;
import com.ehospital.ehospital.service.TreatmentPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/treatment-plans")
public class TreatmentPlanController {

    private final TreatmentPlanService treatmentPlanService;
    private final ClinicalFileRepository clinicalFileRepository;

    public TreatmentPlanController(TreatmentPlanService treatmentPlanService, ClinicalFileRepository clinicalFileRepository) {
        this.treatmentPlanService = treatmentPlanService;
        this.clinicalFileRepository = clinicalFileRepository;
    }

    @GetMapping("/assign")
    public String showForm(Model model) {
        model.addAttribute("treatmentPlan", new TreatmentPlan());

        // ✅ Only include clinical files with status = "Active"
        List<ClinicalFile> activeFiles = clinicalFileRepository.findByStatusIgnoreCase("Active");
        model.addAttribute("clinicalFiles", activeFiles);

        return "assign_treatment";
    }

    @PostMapping("/assign")
    public String assignTreatment(@ModelAttribute TreatmentPlan treatmentPlan) {
        Long fileId = treatmentPlan.getClinicalFile().getId();
        ClinicalFile fullFile = clinicalFileRepository.findById(fileId).orElse(null);
        treatmentPlan.setClinicalFile(fullFile);

        treatmentPlanService.saveTreatmentPlan(treatmentPlan);
        return "redirect:/treatment-plans/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "treatment_success";
    }
}
