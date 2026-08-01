package com.akhilesh.LifeFund.repository;

import com.akhilesh.LifeFund.entity.Campaign;
import com.akhilesh.LifeFund.enums.CampaignStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Page<Campaign> findByCampaignStatus(
            CampaignStatus campaignStatus,
            Pageable pageable
    );

    @Query("""
            SELECT c
            FROM Campaign c
            WHERE c.campaignStatus = :status
            AND (
                    LOWER(c.campaignTitle) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(c.patientDetails.patientName) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(c.patientDetails.diseaseName) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            """)
    Page<Campaign> searchCampaigns(
            @Param("status") CampaignStatus status,
            @Param("search") String search,
            Pageable pageable
    );

}