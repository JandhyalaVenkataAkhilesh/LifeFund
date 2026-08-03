package com.akhilesh.LifeFund.controller;

import com.akhilesh.LifeFund.dto.request.CreateCampaignRequest;
import com.akhilesh.LifeFund.dto.response.CampaignCardResponse;
import com.akhilesh.LifeFund.dto.response.CampaignDetailsResponse;
import com.akhilesh.LifeFund.dto.response.CampaignResponse;
import com.akhilesh.LifeFund.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<CampaignCardResponse>> getAllCampaigns(

            @RequestParam(
                    required = false
            )
            String search,

            @PageableDefault(
                    page = 0,
                    size = 10
            )
            Pageable pageable

    ) {

        Page<CampaignCardResponse> response =
                campaignService.getAllCampaigns(
                        search,
                        pageable
                );

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<CampaignDetailsResponse> getCampaignById(
            @PathVariable Long campaignId) {

        CampaignDetailsResponse response =
                campaignService.getCampaignById(campaignId);

        return ResponseEntity.ok(response);
    }
}