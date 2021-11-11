package com.rhapsodyman.cryptoalerts.service;


import com.rhapsodyman.cryptoalerts.app.Worker;
import com.rhapsodyman.cryptoalerts.domain.TriggerableAlert;
import com.rhapsodyman.cryptoalerts.repository.AlertsRepository;
import com.rhapsodyman.cryptoalerts.thirdparty.quotes.IQuotesProvider;
import com.rhapsodyman.cryptoalerts.thirdparty.sms.ISMSNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service("alertsService")
public class PriceAlertsServiceImpl implements IPriceAlertsService {

    private  final long period = 60; // 60 seconds
    private final ScheduledExecutorService periodicExecutor = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService executor = Executors.newFixedThreadPool(20);

    @Autowired
    private IQuotesProvider quotesProvider;
    @Autowired
    private AlertsRepository alertsRepository;

    @Autowired
    private ISMSNotifier notifier;

    public PriceAlertsServiceImpl(){
        System.out.println("Starting periodicExecutor");
        periodicExecutor.scheduleAtFixedRate(this::createRoundOnAlertChecks, 2L, period, TimeUnit.SECONDS); // TODO - uncomment
    }


    private void createRoundOnAlertChecks(){
        List<TriggerableAlert> alerts = getAlerts();

        for (TriggerableAlert alert : alerts) {
            if (!alert.isWasTriggered()) {
                Worker worker = new Worker(quotesProvider, notifier, this);
                worker.setAlert(alert);
                executor.submit(worker);
            }
        }
    }


    @Override
    public long addAlert(TriggerableAlert triggerableAlert) {
        TriggerableAlert saved = alertsRepository.saveAndFlush(triggerableAlert);
        return saved.getId();
    }

    @Override
    public List<TriggerableAlert> getAlerts() {
        return alertsRepository.findAll();
    }

    @Override
    public Optional<TriggerableAlert> findById(long id) {
        return alertsRepository.findById(id);
    }

    @Override
    public void triggerAlert(long id, String value) {
        TriggerableAlert alert = findById(id).get();
        alert.setWasTriggered(true);
        alert.setTriggeredValue(value);
        alertsRepository.save(alert);
    }

    @Override
    public void deleteAlertById(long id) {
        alertsRepository.deleteById(id);
    }
}