package com.ehospital.ehospital.model;

import jakarta.persistence.*;

@Entity
public class ClinicalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String admissionDate;
    private String initialDiagnosis;
    private String status;
    private String dischargeNote;


    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public ClinicalFile() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }

    public String getInitialDiagnosis() { return initialDiagnosis; }
    public void setInitialDiagnosis(String initialDiagnosis) { this.initialDiagnosis = initialDiagnosis; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public String getDischargeNote() {
        return dischargeNote;
    }

    public void setDischargeNote(String dischargeNote) {
        this.dischargeNote = dischargeNote;
    }

    private boolean monitoringStarted = false;

    public boolean isMonitoringStarted() {
        return monitoringStarted;
    }

    public void setMonitoringStarted(boolean monitoringStarted) {
        this.monitoringStarted = monitoringStarted;
    }

    private String evaluationStatus = "Under Observation"; // default

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

}
