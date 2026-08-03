package com.akhilesh.LifeFund.repository;

import com.akhilesh.LifeFund.entity.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDetailsRepository extends JpaRepository<PatientDetails,Long> {
}
