package com.ehospital.ehospital.controller;

import com.ehospital.ehospital.model.Patient;
import com.ehospital.ehospital.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "register";
    }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient) {
        patientService.registerPatient(patient);
        return "redirect:/patients/success";
    }

    @GetMapping("/success")
    public String registrationSuccess() {
        return "success";
    }

    @GetMapping("/all")
    public String viewAllPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "view_patients";
    }

}
