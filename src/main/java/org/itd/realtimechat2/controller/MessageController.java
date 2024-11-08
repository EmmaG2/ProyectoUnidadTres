package org.itd.realtimechat2.controller;

import org.itd.realtimechat2.entity.Message;
import org.itd.realtimechat2.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/messages")
    @MessageMapping("/messages")
    @SendTo("/topic/messages")
    public void createMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now()); // Establecer la marca de tiempo
        Message savedMessage = messageRepository.save(message);

        Thread hilo = new Thread(() -> {
            messagingTemplate.convertAndSend("/topic/messages", savedMessage);
        });

        hilo.start();

    }

    @GetMapping()
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }
}