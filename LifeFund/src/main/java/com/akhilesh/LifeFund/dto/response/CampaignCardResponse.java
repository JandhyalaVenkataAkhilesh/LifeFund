package com.akhilesh.LifeFund.dto.response;

import com.akhilesh.LifeFund.enums.CampaignStatus;
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
public class CampaignCardResponse {

    private Long campaignId;

    private String patientName;

    private String diseaseName;

    private String hospitalName;

    private String patientPhoto;

    private BigDecimal targetAmount;

    private BigDecimal raisedAmount;

    private Integer donorCount;

    private CampaignStatus campaignStatus;
}