package org.itd.realtimechat2.handler;

import org.itd.realtimechat2.entity.Message;
import org.itd.realtimechat2.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ChatHandler extends TextWebSocketHandler {

    @Autowired
    private MessageRepository messageRepository;

    private final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    // ExecutorService para manejar tareas concurrentes
    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Usa un pool de 10 hilos

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // Guardar mensaje en la base de datos
        Message msg = new Message();
        msg.setContent(payload);
        msg.setSender(session.getId());
        msg.setTimestamp(LocalDateTime.now());
        messageRepository.save(msg);

        // Enviar el mensaje usando el pool de hilos
        executorService.submit(() -> {
            try {
                broadcast(new TextMessage(payload));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void broadcast(TextMessage message) throws Exception {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }

    @PreDestroy
    public void shutdownExecutor() {
        executorService.shutdown();
    }
}
