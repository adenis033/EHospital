package com.ehospital.ehospital.controller;

import com.ehospital.ehospital.model.ClinicalFile;
import com.ehospital.ehospital.model.VitalSign;
import com.ehospital.ehospital.repository.ClinicalFileRepository;
import com.ehospital.ehospital.repository.VitalSignRepository;
import com.ehospital.ehospital.service.VitalSignService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vitals")
public class VitalSignController {

    private final VitalSignService vitalSignService;
    private final ClinicalFileRepository clinicalFileRepository;
    private final VitalSignRepository vitalSignRepository;

    public VitalSignController(VitalSignService vitalSignService,
                               ClinicalFileRepository clinicalFileRepository,
                               VitalSignRepository vitalSignRepository) {
        this.vitalSignService = vitalSignService;
        this.clinicalFileRepository = clinicalFileRepository;
        this.vitalSignRepository = vitalSignRepository;
    }

    @GetMapping("/record")
    public String showVitalForm(Model model) {
        model.addAttribute("vitalSign", new VitalSign());

        // ✅ Only show files with status "Active"
        List<ClinicalFile> activeFiles = clinicalFileRepository.findByStatusIgnoreCase("Active");
        model.addAttribute("clinicalFiles", activeFiles);

        return "record_vitals";
    }

    @PostMapping("/record")
    public String saveVital(@ModelAttribute VitalSign vitalSign) {
        Long fileId = vitalSign.getClinicalFile().getId();
        ClinicalFile fullFile = clinicalFileRepository.findById(fileId).orElse(null);
        vitalSign.setClinicalFile(fullFile);

        vitalSignService.saveVitalSign(vitalSign);
        return "redirect:/vitals/success";
    }

    @GetMapping("/success")
    public String success() {
        return "vitals_success";
    }

    @GetMapping("/list")
    public String viewAllVitals(Model model) {
        List<VitalSign> activeVitals = vitalSignService.getAllVitalSigns()
                .stream()
                .filter(v -> v.getClinicalFile() != null)
                .filter(v -> "Active".equalsIgnoreCase(v.getClinicalFile().getStatus()))
                .toList();

        model.addAttribute("vitalSigns", activeVitals);
        return "vitals_list";
    }

    @GetMapping("/patient/{id}")
    public String viewVitalsForPatient(@PathVariable Long id, Model model) {
        ClinicalFile file = clinicalFileRepository.findById(id).orElse(null);
        if (file == null) return "redirect:/dashboard";

        List<VitalSign> vitals = vitalSignRepository.findByClinicalFile(file);
        model.addAttribute("vitalSigns", vitals);
        model.addAttribute("patient", file.getPatient());
        model.addAttribute("status", file.getEvaluationStatus());
        return "vitals_by_patient";
    }
}
