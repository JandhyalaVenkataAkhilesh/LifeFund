package com.akhilesh.LifeFund.service.Implementation;

import com.akhilesh.LifeFund.dto.request.CreateCampaignRequest;
import com.akhilesh.LifeFund.dto.response.CampaignResponse;
import com.akhilesh.LifeFund.entity.Campaign;
import com.akhilesh.LifeFund.entity.PatientDetails;
import com.akhilesh.LifeFund.enums.CampaignStatus;
import com.akhilesh.LifeFund.repository.CampaignRepository;
import com.akhilesh.LifeFund.repository.PatientDetailsRepository;
import com.akhilesh.LifeFund.service.CampaignService;
import com.akhilesh.LifeFund.utils.FileConstants;
import com.akhilesh.LifeFund.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final FileUploadUtil fileUploadUtil;

    @Override
    public CampaignResponse createCampaign(CreateCampaignRequest request) {

        try {

            String patientPhoto = fileUploadUtil.uploadFile(
                    request.getPatientPhoto(),
                    FileConstants.PATIENT_PHOTOS
            );

            String medicalReport = fileUploadUtil.uploadFile(
                    request.getMedicalReport(),
                    FileConstants.MEDICAL_REPORTS
            );

            String hospitalEstimate = fileUploadUtil.uploadFile(
                    request.getHospitalEstimate(),
                    FileConstants.HOSPITAL_ESTIMATES
            );

            PatientDetails patientDetails = new PatientDetails();
            BeanUtils.copyProperties(request, patientDetails);

            patientDetails.setPatientPhoto(patientPhoto);
            patientDetails.setMedicalReport(medicalReport);
            patientDetails.setHospitalEstimate(hospitalEstimate);

            Campaign campaign = new Campaign();
            BeanUtils.copyProperties(request, campaign);

            campaign.setRaisedAmount(BigDecimal.ZERO);
            campaign.setCampaignStatus(CampaignStatus.ACTIVE);
            campaign.setPatientDetails(patientDetails);

            Campaign savedCampaign = campaignRepository.save(campaign);

            return CampaignResponse.builder()
                    .campaignId(savedCampaign.getId())
                    .campaignTitle(savedCampaign.getCampaignTitle())
                    .patientName(savedCampaign.getPatientDetails().getPatientName())
                    .targetAmount(savedCampaign.getTargetAmount())
                    .raisedAmount(savedCampaign.getRaisedAmount())
                    .campaignStatus(savedCampaign.getCampaignStatus())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload files.", e);
        }
    }
}