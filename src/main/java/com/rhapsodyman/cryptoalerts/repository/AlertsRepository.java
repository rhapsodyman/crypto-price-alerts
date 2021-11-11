package com.rhapsodyman.cryptoalerts.repository;

import com.rhapsodyman.cryptoalerts.domain.TriggerableAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertsRepository extends JpaRepository<TriggerableAlert, Long> {
}