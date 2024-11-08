package org.itd.realtimechat2.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    private String id;
    private String content;
    private String sender;
    private LocalDateTime timestamp;

}
