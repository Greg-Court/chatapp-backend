package com.personal.chatserver.controller;

import com.personal.chatserver.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

// @Controller: This annotation indicates that this class is a Spring MVC controller.
@Controller
public class ChatController {

    // @Autowired: This annotation is used to inject the SimpMessagingTemplate instance, which is a utility class for sending messages through the message broker.
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // recievePublicMessage(): This method handles public messages.
    // @MessageMapping("/message"): Maps the method to handle messages sent to the "/app/message" destination (the "/app" prefix is added automatically).
    // @SendTo("/chatroom/public"): Indicates that the returned Message object will be sent to the "/chatroom/public" destination, which is a public chatroom.
    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public")
    public Message recievePublicMessage(@Payload Message message) {
        // @Payload: The annotation indicates that the message parameter is the payload of the incoming message.
        return message;
    }

    // recievePrivateMessage(): This method handles private messages.
    // @MessageMapping("/private-message"): Maps the method to handle messages sent to the "/app/private-message" destination.
    @MessageMapping("/private-message")
    public Message recievePrivateMessage(@Payload Message message) {
        // simpMessagingTemplate.convertAndSendToUser(): Sends the message to the specified user using the "/user/{username}/private" destination, which represents a private chat between users.
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/David/private
        return message;
    }
}
