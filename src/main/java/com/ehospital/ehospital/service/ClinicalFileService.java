package com.ehospital.ehospital.service;

import com.ehospital.ehospital.model.ClinicalFile;
import com.ehospital.ehospital.repository.ClinicalFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicalFileService {

    private final ClinicalFileRepository clinicalFileRepository;

    public ClinicalFileService(ClinicalFileRepository clinicalFileRepository) {
        this.clinicalFileRepository = clinicalFileRepository;
    }

    public void createClinicalFile(ClinicalFile clinicalFile) {
        clinicalFileRepository.save(clinicalFile);
    }

    public List<ClinicalFile> getAllClinicalFiles() {
        return clinicalFileRepository.findAll();
    }
}
