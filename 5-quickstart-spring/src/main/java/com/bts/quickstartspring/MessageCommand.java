package com.bts.quickstartspring;
import com.bts.common.CommonHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.*;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@Command(name = "messageCommand")
public class MessageCommand implements Callable<Integer> {

    @Autowired private MessageService messageService;

    @Parameters(description = "Message to be sent")
    String message = "Hello";

    public Integer call() throws Exception {
        messageService.sendMessage(message, CommonHelper.randomName());
        return 0;
    }
}