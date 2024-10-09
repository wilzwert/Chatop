package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wilhelm Zwertvaegher
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
