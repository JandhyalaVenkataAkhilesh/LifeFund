package com.akhilesh.LifeFund.service.Implementation;

import com.akhilesh.LifeFund.dto.request.DonationRequest;
import com.akhilesh.LifeFund.dto.request.PaymentVerificationRequest;
import com.akhilesh.LifeFund.dto.response.PaymentResponse;
import com.akhilesh.LifeFund.dto.response.RazorpayOrderResponse;
import com.akhilesh.LifeFund.entity.Campaign;
import com.akhilesh.LifeFund.entity.Donation;
import com.akhilesh.LifeFund.enums.PaymentStatus;
import com.akhilesh.LifeFund.exceptions.CampaignNotFoundException;
import com.akhilesh.LifeFund.exceptions.PaymentVerificationException;
import com.akhilesh.LifeFund.repository.CampaignRepository;
import com.akhilesh.LifeFund.repository.DonationRepository;
import com.akhilesh.LifeFund.service.DonationService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final RazorpayClient razorpayClient;

    private final CampaignRepository campaignRepository;

    private final DonationRepository donationRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    public RazorpayOrderResponse createOrder(
            Long campaignId,
            DonationRequest request) throws RazorpayException {

        Campaign campaign = campaignRepository
                .findById(campaignId)
                .orElseThrow(() ->
                        new CampaignNotFoundException(
                                "Campaign not found with id : " + campaignId
                        ));

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

        options.put("razorpay_order_id",
                request.getRazorpayOrderId());

        options.put("razorpay_payment_id",
                request.getRazorpayPaymentId());

        options.put("razorpay_signature",
                request.getRazorpaySignature());

        boolean isValid =
                Utils.verifyPaymentSignature(
                        options,
                        razorpayKeySecret
                );

        if (!isValid) {

            throw new PaymentVerificationException(
                    "Payment verification failed."
            );

        }

        Campaign campaign =
                campaignRepository.findById(request.getCampaignId())
                        .orElseThrow(() ->
                                new CampaignNotFoundException(
                                        "Campaign not found with id : "
                                                + request.getCampaignId()
                                ));

        Donation donation = new Donation();

        donation.setDonorName(
                request.getDonorName());

        donation.setDonorEmail(
                request.getDonorEmail());

        donation.setDonorPhoneNumber(
                request.getDonorPhoneNumber());

        donation.setAmount(
                request.getAmount());

        donation.setPaymentId(
                request.getRazorpayPaymentId());

        donation.setPaymentStatus(
                PaymentStatus.SUCCESS);

        donation.setDonatedAt(
                LocalDateTime.now());

        donation.setCampaign(
                campaign);

        donationRepository.save(donation);

        campaign.setRaisedAmount(

                campaign.getRaisedAmount()
                        .add(request.getAmount())

        );

        campaignRepository.save(campaign);

        return PaymentResponse.builder()
                .success(true)
                .message("Payment verified successfully.")
                .build();

    }

}