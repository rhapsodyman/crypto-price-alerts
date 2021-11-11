package com.rhapsodyman.cryptoalerts.service;

import com.rhapsodyman.cryptoalerts.domain.AlertDescription;
import com.rhapsodyman.cryptoalerts.domain.TriggerableAlert;

import java.util.List;
import java.util.Optional;

public interface IPriceAlertsService {

    long addAlert(TriggerableAlert triggerableAlert);

    List<TriggerableAlert> getAlerts();

    Optional<TriggerableAlert> findById(long id);

    void triggerAlert(long id, String value);

    void deleteAlertById(long id);


}