package com.openclassrooms.chatop.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Wilhelm Zwertvaegher
 */
@Data
@Getter
@Setter
@Entity
@Table(name="USERS")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String password;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Rental> rentals;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // let db handle cascade deletion
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Message> messages;

    /**
     * Override toString method to avoid never ending loops on Message::toString or Rental::toString
     * @return String the string representation of this User
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", name=" + name + ", createdAd=" + createdAt+", updatedAt=" + updatedAt+"],"
                + "rentalsCount=" + rentals.size() + ", messagesCount=" + messages.size();
    }
}
