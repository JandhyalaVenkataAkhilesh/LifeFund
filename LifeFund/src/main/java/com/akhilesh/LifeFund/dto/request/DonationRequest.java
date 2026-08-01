package com.akhilesh.LifeFund.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DonationRequest {

    private String donorName;

    private String donorEmail;

    private String donorPhoneNumber;

    private BigDecimal amount;

}