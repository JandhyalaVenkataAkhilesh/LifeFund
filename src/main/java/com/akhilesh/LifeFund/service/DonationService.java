package com.akhilesh.LifeFund.service;

import com.akhilesh.LifeFund.dto.request.DonationRequest;
import com.akhilesh.LifeFund.dto.request.PaymentVerificationRequest;
import com.akhilesh.LifeFund.dto.response.PaymentResponse;
import com.akhilesh.LifeFund.dto.response.RazorpayOrderResponse;
import com.razorpay.RazorpayException;

public interface DonationService {

    RazorpayOrderResponse createOrder(
            Long campaignId,
            DonationRequest request
    ) throws RazorpayException;

    PaymentResponse verifyPayment(
            PaymentVerificationRequest request
    ) throws RazorpayException;

}