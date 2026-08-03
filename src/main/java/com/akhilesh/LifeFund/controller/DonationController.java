package com.akhilesh.LifeFund.controller;

import com.akhilesh.LifeFund.dto.request.DonationRequest;
import com.akhilesh.LifeFund.dto.request.PaymentVerificationRequest;
import com.akhilesh.LifeFund.dto.response.PaymentResponse;
import com.akhilesh.LifeFund.dto.response.RazorpayOrderResponse;
import com.akhilesh.LifeFund.service.DonationService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping("/{campaignId}/donate")
    public ResponseEntity<RazorpayOrderResponse> createOrder(
            @PathVariable Long campaignId,
            @RequestBody DonationRequest request
    ) throws RazorpayException {

        RazorpayOrderResponse response =
                donationService.createOrder(campaignId, request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/payment/verify")
    public ResponseEntity<PaymentResponse> verifyPayment(
            @RequestBody PaymentVerificationRequest request)
            throws RazorpayException {

        PaymentResponse response =
                donationService.verifyPayment(request);

        return ResponseEntity.ok(response);
    }
}