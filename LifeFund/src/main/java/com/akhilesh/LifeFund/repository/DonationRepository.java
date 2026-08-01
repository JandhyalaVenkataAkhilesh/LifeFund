package com.akhilesh.LifeFund.repository;

import com.akhilesh.LifeFund.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation,Long> {
}
