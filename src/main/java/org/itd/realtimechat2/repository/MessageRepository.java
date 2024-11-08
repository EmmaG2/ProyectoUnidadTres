package org.itd.realtimechat2.repository;

import org.itd.realtimechat2.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository  extends MongoRepository<Message, String> {
}
