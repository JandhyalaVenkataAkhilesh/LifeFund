package com.akhilesh.LifeFund.dto.response;

import com.akhilesh.LifeFund.enums.BloodGroup;
import com.akhilesh.LifeFund.enums.CampaignStatus;
import com.akhilesh.LifeFund.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignDetailsResponse {

    private Long campaignId;

    private String campaignTitle;

    private String campaignDescription;

    private String patientName;

    private Integer age;

    private Gender gender;

    private BloodGroup bloodGroup;

    private String diseaseName;

    private String hospitalName;

    private String doctorName;

    private String treatmentRequired;

    private String description;

    private String patientPhoto;

    private String medicalReport;

    private String hospitalEstimate;

    private BigDecimal targetAmount;

    private BigDecimal raisedAmount;

    private Integer donorCount;

    private CampaignStatus campaignStatus;
}