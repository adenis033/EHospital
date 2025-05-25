package com.ehospital.ehospital.simulation;

import com.ehospital.ehospital.model.ClinicalFile;
import com.ehospital.ehospital.model.VitalSign;
import com.ehospital.ehospital.repository.ClinicalFileRepository;
import com.ehospital.ehospital.repository.VitalSignRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class SensorSimulator {

    private final ClinicalFileRepository clinicalFileRepository;
    private final VitalSignRepository vitalSignRepository;
    private final Random random = new Random();

    public SensorSimulator(ClinicalFileRepository clinicalFileRepository, VitalSignRepository vitalSignRepository) {
        this.clinicalFileRepository = clinicalFileRepository;
        this.vitalSignRepository = vitalSignRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void simulateSensorData() {
        List<ClinicalFile> activeFiles = clinicalFileRepository.findAll()
                .stream()
                .filter(file -> "Active".equalsIgnoreCase(file.getStatus()))
                .filter(ClinicalFile::isMonitoringStarted)
                .toList();

        if (activeFiles.isEmpty()) return;

        ClinicalFile file = activeFiles.get(random.nextInt(activeFiles.size()));

        // ⬇️ 80% chance normal vitals, 20% abnormal
        boolean abnormal = random.nextDouble() < 0.2;

        double temp = abnormal ? 38.5 + random.nextDouble(1.5) : 36 + random.nextDouble(2); // 36–38 normal, >38.5 abnormal
        int systolic = abnormal ? 140 + random.nextInt(30) : 100 + random.nextInt(40);       // abnormal >140
        int diastolic = abnormal ? 90 + random.nextInt(20) : 60 + random.nextInt(20);        // abnormal >90
        int hr = abnormal ? 110 + random.nextInt(30) : 60 + random.nextInt(40);              // normal 60–100

        VitalSign vital = new VitalSign();
        vital.setClinicalFile(file);
        vital.setTemperature(temp);
        vital.setBloodPressure(systolic + "/" + diastolic);
        vital.setHeartRate(hr);
        vital.setTimestamp(LocalDateTime.now());

        vitalSignRepository.save(vital);
        System.out.println((abnormal ? "🚨 Abnormal" : "✅ Normal") + " vitals added for File #" + file.getId());

        evaluatePatientStatus(file);
    }

    private void evaluatePatientStatus(ClinicalFile file) {
        List<VitalSign> last3 = vitalSignRepository
                .findTop3ByClinicalFileOrderByTimestampDesc(file);

        if (last3.size() < 3) {
            file.setEvaluationStatus("Under Observation");
        } else {
            boolean allStable = last3.stream().allMatch(v ->
                    v.getTemperature() <= 38.0 &&
                            v.getHeartRate() >= 60 && v.getHeartRate() <= 100
            );

            file.setEvaluationStatus(allStable ? "Stable" : "Under Observation");
        }

        clinicalFileRepository.save(file);
    }
}
