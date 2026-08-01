package com.akhilesh.LifeFund.service.Implementation;

import com.akhilesh.LifeFund.dto.request.DonationRequest;
import com.akhilesh.LifeFund.dto.request.PaymentVerificationRequest;
import com.akhilesh.LifeFund.dto.response.PaymentResponse;
import com.akhilesh.LifeFund.dto.response.RazorpayOrderResponse;
import com.akhilesh.LifeFund.entity.Campaign;
import com.akhilesh.LifeFund.repository.CampaignRepository;
import com.akhilesh.LifeFund.service.DonationService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final RazorpayClient razorpayClient;
    private final CampaignRepository campaignRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    public RazorpayOrderResponse createOrder(
            Long campaignId,
            DonationRequest request) throws RazorpayException {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() ->
                        new RuntimeException("Campaign not found."));

        String receipt = "CAM" + campaignId + "_" + System.currentTimeMillis();

        JSONObject options = new JSONObject();

        options.put(
                "amount",
                request.getAmount()
                        .multiply(BigDecimal.valueOf(100))
                        .intValue()
        );

        options.put("currency", "INR");

        options.put("receipt", receipt);

        Order order = razorpayClient.orders.create(options);

        return RazorpayOrderResponse.builder()
                .orderId(order.get("id").toString())
                .amount(request.getAmount())
                .currency(order.get("currency").toString())
                .keyId(keyId)
                .build();
    }

    @Override
    public PaymentResponse verifyPayment(
            PaymentVerificationRequest request)
            throws RazorpayException {

        JSONObject options = new JSONObject();

        options.put("razorpay_order_id", request.getRazorpayOrderId());

        options.put("razorpay_payment_id", request.getRazorpayPaymentId());

        options.put("razorpay_signature", request.getRazorpaySignature());

        boolean isValid = Utils.verifyPaymentSignature(options,
                razorpayKeySecret);

        if (!isValid) {
            throw new RuntimeException("Payment verification failed.");
        }

        return null;
    }
}