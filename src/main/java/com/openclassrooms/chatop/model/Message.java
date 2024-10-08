package com.openclassrooms.chatop.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author Wilhelm Zwertvaegher
 */
@Data
@Entity
@Table(name="MESSAGES")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 2000)
    private String message;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    // let db handle cascade deletion
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Nullable
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = true)
    // let db handle cascade deletion
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Nullable
    private Rental rental;

    /**
     * Override toString method to avoid never ending loops on Rental::toString or User::toString
     * @return String the string representation of this Message
     */
    @Override
    public String toString() {
        return "Message [id=" + id + ", message=" + message + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "],"
            +(user != null ? " Owner [id=" + user.getId()+", email=" + user.getEmail()+" ]," : " Owner unknown,")
            +(rental != null ? " Rental [id=" + rental.getId() + ", name="+rental.getName()+" ]" : "Rental unknown");
    }
}
