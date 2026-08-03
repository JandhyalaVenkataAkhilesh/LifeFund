package com.akhilesh.LifeFund.dto.response;

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
public class DashboardResponse {

    private String userName;

    private Long totalCampaigns;

    private Long totalDonors;

    private BigDecimal totalAmountRaised;

}