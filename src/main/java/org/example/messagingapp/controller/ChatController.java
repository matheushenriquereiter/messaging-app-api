package org.example.messagingapp.controller;

import org.example.messagingapp.dto.MessageSendDTO;
import org.example.messagingapp.model.Message;
import org.example.messagingapp.repository.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    MessageRepository messageRepository;

    public ChatController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message saveAndBroadcastMessage(MessageSendDTO messageSendDTO) {
        Message message = new Message(messageSendDTO.content());

        return messageRepository.save(message);
    }
}
