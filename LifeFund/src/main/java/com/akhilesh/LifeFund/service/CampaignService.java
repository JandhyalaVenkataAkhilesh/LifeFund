package com.akhilesh.LifeFund.service;

import com.akhilesh.LifeFund.dto.request.CreateCampaignRequest;
import com.akhilesh.LifeFund.dto.response.CampaignResponse;

public interface CampaignService {
    CampaignResponse createCampaign(CreateCampaignRequest request);
}
