package com.bts.quickstartspring;

import org.slf4j.*;
import org.springframework.stereotype.Service;

@Service("MessageService")
public class MessageServiceImpl implements MessageService{

    private static final Logger LOGGER= LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public void sendMessage(String message, String to) {
        LOGGER.info("Message {} sent to {}", message, to);
        System.out.println("Message " + message + " sent to " + to);
    }
}
