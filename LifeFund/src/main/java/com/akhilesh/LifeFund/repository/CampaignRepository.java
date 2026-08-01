package com.akhilesh.LifeFund.repository;

import com.akhilesh.LifeFund.entity.Campaign;
import com.akhilesh.LifeFund.enums.CampaignStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
    Page<Campaign> findByCampaignStatus(
            CampaignStatus campaignStatus,
            Pageable pageable
    );
}
