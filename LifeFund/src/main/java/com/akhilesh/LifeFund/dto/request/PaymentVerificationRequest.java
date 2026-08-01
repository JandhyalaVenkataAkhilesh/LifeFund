package com.akhilesh.LifeFund.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PaymentVerificationRequest {

    private Long campaignId;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

    private String donorName;

    private String donorEmail;

    private String donorPhoneNumber;

    private BigDecimal amount;

}