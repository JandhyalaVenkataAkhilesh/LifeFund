package com.akhilesh.LifeFund.service;

import com.akhilesh.LifeFund.dto.request.CreateCampaignRequest;
import com.akhilesh.LifeFund.dto.response.CampaignCardResponse;
import com.akhilesh.LifeFund.dto.response.CampaignDetailsResponse;
import com.akhilesh.LifeFund.dto.response.CampaignResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CampaignService {

    CampaignResponse createCampaign(
            CreateCampaignRequest request
    );

    Page<CampaignCardResponse> getAllCampaigns(
            String search,
            Pageable pageable
    );

    CampaignDetailsResponse getCampaignById(
            Long campaignId
    );

}