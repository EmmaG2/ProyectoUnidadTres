package org.itd.realtimechat2.service;

import org.itd.realtimechat2.entity.Message;
import org.itd.realtimechat2.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getChatHistory() {
        return messageRepository.findAll();
    }
}
