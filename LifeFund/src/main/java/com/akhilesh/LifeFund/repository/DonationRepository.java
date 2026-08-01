package com.akhilesh.LifeFund.repository;

import com.akhilesh.LifeFund.entity.Campaign;
import com.akhilesh.LifeFund.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,Long> {
    long countByCampaign(Campaign campaign);
    Optional<Donation> findByPaymentId(String paymentId);
}
