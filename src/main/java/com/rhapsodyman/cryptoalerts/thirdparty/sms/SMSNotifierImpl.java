package com.rhapsodyman.cryptoalerts.thirdparty.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SMSNotifierImpl implements ISMSNotifier {

    // Find your Account Sid and Token at twilio.com/user/account
    private final String from;
    private final String accountSID;
    private final String authToken;


    public SMSNotifierImpl(@Value("${sms.from}") String from, @Value("${sms.sid}") String accountSID, @Value("${sms.authToken}") String authToken) {
        this.from = from;
        this.accountSID = accountSID;
        this.authToken = authToken;
    }


    @Override
    public void sendSMSNotification(String to, String message) {
        System.out.println(String.format("Sending SMS notification to %s with content %s", to, message));
        Twilio.init(accountSID, authToken);

        Message msg = Message.creator(new PhoneNumber(to),
                new PhoneNumber(from),
                message).create();

//        System.out.println(msg.getSid());
    }
}