package com.akhilesh.LifeFund.entity;
import com.akhilesh.LifeFund.enums.BloodGroup;
import com.akhilesh.LifeFund.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patient_details")
@Getter
@Setter
@NoArgsConstructor
public class PatientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
    private String diseaseName;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String doctorName;

    @Column(nullable = false)
    private String treatmentRequired;

    @Column(nullable = false, length = 3000)
    private String description;

    private String patientPhoto;

    private String medicalReport;

    private String hospitalEstimate;
}
