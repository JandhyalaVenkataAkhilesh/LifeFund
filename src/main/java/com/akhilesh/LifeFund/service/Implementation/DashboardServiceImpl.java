package com.akhilesh.LifeFund.service.Implementation;

import com.akhilesh.LifeFund.dto.response.DashboardResponse;
import com.akhilesh.LifeFund.entity.User;
import com.akhilesh.LifeFund.enums.CampaignStatus;
import com.akhilesh.LifeFund.enums.PaymentStatus;
import com.akhilesh.LifeFund.repository.CampaignRepository;
import com.akhilesh.LifeFund.repository.DonationRepository;
import com.akhilesh.LifeFund.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CampaignRepository campaignRepository;

    private final DonationRepository donationRepository;

    @Override
    public DashboardResponse getDashboard() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        User user = (User) authentication.getPrincipal();

        Long totalCampaigns =
                campaignRepository.countByCampaignStatus(
                        CampaignStatus.ACTIVE
                );

        Long totalDonors =
                donationRepository.countDistinctDonors(
                        PaymentStatus.SUCCESS
                );

        BigDecimal totalAmountRaised =
                donationRepository.getTotalAmountRaised(
                        PaymentStatus.SUCCESS
                );

        return DashboardResponse.builder()
                .userName(user.getName())
                .totalCampaigns(totalCampaigns)
                .totalDonors(totalDonors)
                .totalAmountRaised(totalAmountRaised)
                .build();

    }

}