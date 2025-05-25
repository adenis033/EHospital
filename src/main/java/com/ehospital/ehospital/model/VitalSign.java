package com.ehospital.ehospital.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VitalSign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;
    private String bloodPressure;
    private int heartRate;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "clinical_file_id")
    private ClinicalFile clinicalFile;

    public VitalSign() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public int getHeartRate() { return heartRate; }
    public void setHeartRate(int heartRate) { this.heartRate = heartRate; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public ClinicalFile getClinicalFile() { return clinicalFile; }
    public void setClinicalFile(ClinicalFile clinicalFile) { this.clinicalFile = clinicalFile; }
}
