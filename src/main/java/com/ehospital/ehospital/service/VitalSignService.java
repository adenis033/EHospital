package com.ehospital.ehospital.service;

import com.ehospital.ehospital.model.ClinicalFile;
import com.ehospital.ehospital.model.VitalSign;
import com.ehospital.ehospital.repository.ClinicalFileRepository;
import com.ehospital.ehospital.repository.VitalSignRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VitalSignService {

    private final VitalSignRepository vitalSignRepository;
    private final ClinicalFileRepository clinicalFileRepository;

    public VitalSignService(VitalSignRepository vitalSignRepository,
                            ClinicalFileRepository clinicalFileRepository) {
        this.vitalSignRepository = vitalSignRepository;
        this.clinicalFileRepository = clinicalFileRepository;
    }

    public void saveVitalSign(VitalSign vitalSign) {
        vitalSignRepository.save(vitalSign);

        ClinicalFile clinicalFile = vitalSign.getClinicalFile();
        if (!clinicalFile.isMonitoringStarted()) {
            clinicalFile.setMonitoringStarted(true);
            clinicalFileRepository.save(clinicalFile);
        }
    }

    public List<VitalSign> getAllVitalSigns() {
        return vitalSignRepository.findAll();
    }

}
