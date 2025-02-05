package com.airbnb2.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwillioSmsService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.fromPhoneNumber}")
    private String fromNumber;

    public void sendSms(String to, String body) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(fromNumber),
                        body)
                .create();
        System.out.println("Message SID: " + message.getSid());
    }
}
