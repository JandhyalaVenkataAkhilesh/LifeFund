package com.akhilesh.LifeFund.dto.response;

import com.akhilesh.LifeFund.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CampaignResponse {

    private Long campaignId;
    private String campaignTitle;
    private String patientName;
    private BigDecimal targetAmount;
    private BigDecimal raisedAmount;
    private CampaignStatus campaignStatus;
}