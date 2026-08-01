package com.akhilesh.LifeFund.controller;

import com.akhilesh.LifeFund.dto.request.CreateCampaignRequest;
import com.akhilesh.LifeFund.dto.response.CampaignResponse;
import com.akhilesh.LifeFund.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CampaignResponse> createCampaign(
            @ModelAttribute CreateCampaignRequest request) {

        CampaignResponse response = campaignService.createCampaign(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}