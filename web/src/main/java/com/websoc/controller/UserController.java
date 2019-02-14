package com.websoc.controller;

import com.google.gson.Gson;
import com.websoc.domain.User;
import java.security.Principal;
import java.util.Map;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(@Payload String message, Principal principal) {
        Map map = new Gson().fromJson(message, Map.class);
        String name = map.get("name").toString();
        User user = new User(name);

        return new Gson().toJson(user);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}