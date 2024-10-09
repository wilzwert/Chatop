package com.openclassrooms.chatop.model;

import com.openclassrooms.chatop.listener.RentalListener;
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
@Table(name="RENTALS")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({RentalListener.class, AuditingEntityListener.class})
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float surface;
    private float price;
    private String picture;
    @Column(length = 2000)
    private String description;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rental")
    private List<Message> messages;

    /**
     * Override toString method to avoid never ending loops on User::toString or Message::toString
     * @return String the string representation of this Rental
     */
    @Override
    public String toString() {
        return "Rental [id=" + id + ", name=" + name + ", createdAd=" + createdAt+", updatedAt=" + updatedAt+"],"
                + "Owner [id=" + owner.getId() + "email=" +owner.getEmail() + "],"
                +" messagesCount=" + messages.size();
    }
}
