package com.ehospital.ehospital.repository;

import com.ehospital.ehospital.model.ClinicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicalFileRepository extends JpaRepository<ClinicalFile, Long> {

    List<ClinicalFile> findByStatusIgnoreCase(String status);
    long countByStatusIgnoreCase(String status);
}
