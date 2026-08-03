package com.akhilesh.LifeFund.dto.request;

import com.akhilesh.LifeFund.enums.BloodGroup;
import com.akhilesh.LifeFund.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateCampaignRequest {

    // Campaign Details
    private String campaignTitle;
    private String campaignDescription;
    private BigDecimal targetAmount;

    // Patient Details
    private String patientName;
    private Integer age;
    private Gender gender;
    private BloodGroup bloodGroup;
    private String diseaseName;
    private String hospitalName;
    private String doctorName;
    private String treatmentRequired;
    private String description;

    // File Uploads
    private MultipartFile patientPhoto;
    private MultipartFile medicalReport;
    private MultipartFile hospitalEstimate;
}