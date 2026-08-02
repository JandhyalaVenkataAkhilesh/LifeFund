package com.akhilesh.LifeFund.repository;

import com.akhilesh.LifeFund.entity.Campaign;
import com.akhilesh.LifeFund.entity.Donation;
import com.akhilesh.LifeFund.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,Long> {

    long countByCampaign(Campaign campaign);

    Optional<Donation> findByPaymentId(String paymentId);

    long countByPaymentStatus(
            PaymentStatus paymentStatus
    );

    @Query("""
            SELECT COALESCE(SUM(d.amount),0)
            FROM Donation d
            WHERE d.paymentStatus = :paymentStatus
            """)
    BigDecimal getTotalAmountRaised(
            PaymentStatus paymentStatus
    );

}