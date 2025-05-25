package com.ehospital.ehospital.controller;

import com.ehospital.ehospital.model.ClinicalFile;
import com.ehospital.ehospital.model.Patient;
import com.ehospital.ehospital.repository.PatientRepository;
import com.ehospital.ehospital.repository.VitalSignRepository;
import com.ehospital.ehospital.service.ClinicalFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Controller
@RequestMapping("/clinical-files")
public class ClinicalFileController {

    private final ClinicalFileService clinicalFileService;
    private final PatientRepository patientRepository;
    private final VitalSignRepository vitalSignRepository;

    public ClinicalFileController(ClinicalFileService clinicalFileService,
                                  PatientRepository patientRepository,
                                  VitalSignRepository vitalSignRepository) {
        this.clinicalFileService = clinicalFileService;
        this.patientRepository = patientRepository;
        this.vitalSignRepository = vitalSignRepository;
    }

    // ➕ Create a clinical file
    @GetMapping("/create")
    public String showClinicalFileForm(Model model) {
        model.addAttribute("clinicalFile", new ClinicalFile());
        model.addAttribute("patients", patientRepository.findAll());
        return "create_clinical_file";
    }

    @PostMapping("/create")
    public String createClinicalFile(@ModelAttribute ClinicalFile clinicalFile) {
        Long patientId = clinicalFile.getPatient().getId();
        Patient fullPatient = patientRepository.findById(patientId).orElse(null);
        clinicalFile.setPatient(fullPatient);

        clinicalFileService.createClinicalFile(clinicalFile);
        return "redirect:/clinical-files/success";
    }

    @GetMapping("/success")
    public String creationSuccess() {
        return "clinical_file_success";
    }

    // 🏥 Discharge patient
    @Transactional
    @GetMapping("/discharge")
    public String showDischargeForm(Model model) {
        List<ClinicalFile> activeFiles = clinicalFileService.getAllClinicalFiles()
                .stream()
                .filter(f -> "Active".equalsIgnoreCase(f.getStatus()))
                .toList();

        model.addAttribute("files", activeFiles);
        model.addAttribute("fileToDischarge", new ClinicalFile());
        return "discharge_patient";
    }

    @PostMapping("/discharge")
    public String dischargePatient(@ModelAttribute("fileToDischarge") ClinicalFile fileInput, Model model) {
        ClinicalFile file = clinicalFileService.getAllClinicalFiles()
                .stream()
                .filter(f -> f.getId().equals(fileInput.getId()))
                .findFirst()
                .orElse(null);

        if (file == null) {
            return "redirect:/clinical-files/discharge";
        }

        // ❌ If not stable, block discharge and show error
        if (!"Stable".equalsIgnoreCase(file.getEvaluationStatus())) {
            model.addAttribute("errorMessage", "❌ Patient cannot be discharged unless marked as Stable.");
            List<ClinicalFile> activeFiles = clinicalFileService.getAllClinicalFiles()
                    .stream()
                    .filter(f -> "Active".equalsIgnoreCase(f.getStatus()))
                    .toList();
            model.addAttribute("files", activeFiles);
            model.addAttribute("fileToDischarge", new ClinicalFile());
            return "discharge_patient";
        }

        // ✅ Stable → allow discharge
        file.setStatus("Discharged");
        file.setDischargeNote(fileInput.getDischargeNote());
        vitalSignRepository.deleteByClinicalFile(file);
        file.setEvaluationStatus(null);

        clinicalFileService.createClinicalFile(file);
        return "redirect:/clinical-files/discharge-success";
    }


    @GetMapping("/discharge-success")
    public String dischargeSuccess() {
        return "discharge_success";
    }

    // ♻️ Reactivate discharged patient (GUARD DOCTOR)
    @GetMapping("/reactivate")
    public String showReactivationForm(Model model) {
        List<ClinicalFile> dischargedFiles = clinicalFileService.getAllClinicalFiles()
                .stream()
                .filter(f -> "Discharged".equalsIgnoreCase(f.getStatus()))
                .toList();

        model.addAttribute("dischargedFiles", dischargedFiles);
        model.addAttribute("fileToReactivate", new ClinicalFile());
        return "reactivate_patient";
    }

    @PostMapping("/reactivate")
    public String reactivatePatient(@ModelAttribute("fileToReactivate") ClinicalFile fileInput) {
        ClinicalFile file = clinicalFileService.getAllClinicalFiles()
                .stream()
                .filter(f -> f.getId().equals(fileInput.getId()))
                .findFirst()
                .orElse(null);

        if (file != null) {
            file.setStatus("Active");

            // ✅ update disease/diagnosis only if provided
            if (fileInput.getInitialDiagnosis() != null && !fileInput.getInitialDiagnosis().isBlank()) {
                file.setInitialDiagnosis(fileInput.getInitialDiagnosis());
            }

            clinicalFileService.createClinicalFile(file);
        }

        return "redirect:/clinical-files/reactivate-success";
    }

    @GetMapping("/reactivate-success")
    public String reactivateSuccess() {
        return "reactivate_success";
    }
}
