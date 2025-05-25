package com.ehospital.ehospital.repository;

import com.ehospital.ehospital.model.ClinicalFile;
import com.ehospital.ehospital.model.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {

    long countByTimestampAfter(LocalDateTime localDateTime);

    List<VitalSign> findTop3ByClinicalFileOrderByTimestampDesc(ClinicalFile file);

    List<VitalSign> findByClinicalFile(ClinicalFile file);

    @Transactional
    void deleteByClinicalFile(ClinicalFile file);

}
