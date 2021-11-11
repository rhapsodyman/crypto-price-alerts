package com.rhapsodyman.cryptoalerts.app;

import com.rhapsodyman.cryptoalerts.domain.PriceOperator;
import com.rhapsodyman.cryptoalerts.domain.TriggerableAlert;
import com.rhapsodyman.cryptoalerts.service.IPriceAlertsService;
import com.rhapsodyman.cryptoalerts.thirdparty.quotes.IQuotesProvider;
import com.rhapsodyman.cryptoalerts.thirdparty.sms.ISMSNotifier;

import java.math.BigDecimal;

public class Worker implements Runnable {

    private final IQuotesProvider quotesProvider;
    private final ISMSNotifier notifier;
    private final IPriceAlertsService priceAlertsService;
    private TriggerableAlert alert;

    public Worker(IQuotesProvider quotesProvider, ISMSNotifier notifier, IPriceAlertsService priceAlertsService) {
        this.quotesProvider = quotesProvider;
        this.notifier = notifier;
        this.priceAlertsService = priceAlertsService;
    }

    public void setAlert(TriggerableAlert alert) {
        this.alert = alert;
    }


    @Override
    public void run() {
        BigDecimal cryptoPrice = null;
        try {
            cryptoPrice = quotesProvider.getCryptoPrice(alert.getSymbol());
        } catch (Exception e) {
            System.err.println("Exception getting price data: " + e.getMessage());
            return;
        }

        String alertMessage = "Alert triggered:  " + alert.toString() + "\nCurrent price is: " + cryptoPrice.toString();

        if (
                (alert.getOperator() == PriceOperator.LESS_THAN && cryptoPrice.compareTo(new BigDecimal(alert.getTargetValue())) < 0)
                        || (alert.getOperator() == PriceOperator.GREATER_THAN && cryptoPrice.compareTo(new BigDecimal(alert.getTargetValue())) > 0)
        ) {

            notifier.sendSMSNotification(alert.getOwner().getPhone(), alertMessage);
            priceAlertsService.triggerAlert(alert.getId(), cryptoPrice.toString());
        }
    }
}
