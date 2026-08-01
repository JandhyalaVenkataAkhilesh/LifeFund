package com.akhilesh.LifeFund.repository;

import com.akhilesh.LifeFund.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
}
