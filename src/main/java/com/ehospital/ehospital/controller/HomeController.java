package com.ehospital.ehospital.controller;

import com.ehospital.ehospital.repository.ClinicalFileRepository;
import com.ehospital.ehospital.repository.PatientRepository;
import com.ehospital.ehospital.repository.TreatmentPlanRepository;
import com.ehospital.ehospital.repository.VitalSignRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    private final PatientRepository patientRepository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final VitalSignRepository vitalSignRepository;
    private final ClinicalFileRepository clinicalFileRepository; // ✅ added

    public HomeController(PatientRepository patientRepository,
                          TreatmentPlanRepository treatmentPlanRepository,
                          VitalSignRepository vitalSignRepository,
                          ClinicalFileRepository clinicalFileRepository) {
        this.patientRepository = patientRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.vitalSignRepository = vitalSignRepository;
        this.clinicalFileRepository = clinicalFileRepository;
    }

    @GetMapping("/")
    public String rootRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "home_public";
    }

    @GetMapping("/home")
    public String homeRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "home_public";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        long totalPatients = patientRepository.count();
        long dischargedCount = clinicalFileRepository.countByStatusIgnoreCase("Discharged");
        long activeCount = clinicalFileRepository.countByStatusIgnoreCase("Active");

        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("dischargedCount", dischargedCount);
        model.addAttribute("activeCount", activeCount);

        return "home";
    }
}
