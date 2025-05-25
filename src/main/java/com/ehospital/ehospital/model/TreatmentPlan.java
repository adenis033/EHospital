package com.ehospital.ehospital.model;

import jakarta.persistence.*;

@Entity
public class TreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medication;
    private String procedures;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "clinical_file_id")
    private ClinicalFile clinicalFile;

    public TreatmentPlan() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }

    public String getProcedures() { return procedures; }
    public void setProcedures(String procedures) { this.procedures = procedures; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public ClinicalFile getClinicalFile() { return clinicalFile; }
    public void setClinicalFile(ClinicalFile clinicalFile) { this.clinicalFile = clinicalFile; }
}
