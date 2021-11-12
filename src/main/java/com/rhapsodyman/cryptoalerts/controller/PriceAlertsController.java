package com.rhapsodyman.cryptoalerts.controller;


import com.rhapsodyman.cryptoalerts.domain.AlertDescription;
import com.rhapsodyman.cryptoalerts.domain.TriggerableAlert;
import com.rhapsodyman.cryptoalerts.security.services.UserDetailsImpl;
import com.rhapsodyman.cryptoalerts.service.IPriceAlertsService;
import com.rhapsodyman.cryptoalerts.service.IUsersService;
import com.rhapsodyman.cryptoalerts.thirdparty.quotes.IQuotesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("priceAlerts")
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PriceAlertsController {

    @Autowired
    IPriceAlertsService alertsService;

    @Autowired
    IUsersService usersService;

    @Autowired
    IQuotesProvider quotesProvider;


    @GetMapping("/alerts")
    public ResponseEntity<List<TriggerableAlert>> getAllAlerts() {
        String userName = getUserNameFromSecurityContext();
        List<TriggerableAlert> alerts = usersService.findByUsername(userName).get().getAlerts();

        if (alerts.isEmpty()) {
            return new ResponseEntity<List<TriggerableAlert>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TriggerableAlert>>(alerts, HttpStatus.OK);
    }

    @GetMapping(value = "/alerts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TriggerableAlert> getAlert(@PathVariable("id") long id) {
        Optional<TriggerableAlert> alert = alertsService.findById(id);
        if (!alert.isPresent()) {
            return new ResponseEntity<TriggerableAlert>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TriggerableAlert>(alert.get(), HttpStatus.OK);
    }



    @DeleteMapping(value = "/alerts/{id}")
    public ResponseEntity<TriggerableAlert> deleteAlert(@PathVariable("id") long id) {
        Optional<TriggerableAlert> alert = alertsService.findById(id);
        if (!alert.isPresent()) {
            return new ResponseEntity<TriggerableAlert>(HttpStatus.NOT_FOUND);
        }

        String userName = getUserNameFromSecurityContext();

        TriggerableAlert triggerableAlert = alert.get();
        if (triggerableAlert.getOwner().getUsername().equals(userName)) {
            alertsService.deleteAlertById(id);
            return new ResponseEntity<TriggerableAlert>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<TriggerableAlert>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/alerts")
    public ResponseEntity<Void> addAlert(@RequestBody AlertDescription alert, UriComponentsBuilder ucBuilder) {

        String userName = getUserNameFromSecurityContext();
        TriggerableAlert triggerableAlert = new TriggerableAlert(alert.getSymbol(), alert.getOperator(), alert.getTargetValue());

        triggerableAlert.setOwner(usersService.findByUsername(userName).get());
        long id = alertsService.addAlert(triggerableAlert);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/alerts/{id}").buildAndExpand(id).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    @GetMapping("/crypto/isValid")
    public ResponseEntity<String> isValidCrypto(@RequestParam(name = "symbol") String symbol) {
        try {
            quotesProvider.getCryptoPrice(symbol);
            return new ResponseEntity<String>("Valid", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<String>("Invalid", HttpStatus.OK);
        }
    }



    private String getUserNameFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return  userDetails.getUsername();
    }
}