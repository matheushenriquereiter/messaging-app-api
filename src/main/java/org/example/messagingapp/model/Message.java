package org.example.messagingapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq_gen")
    @SequenceGenerator(name = "message_seq_gen", sequenceName = "message_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Message content cannot be null or empty")
    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "send_at", nullable = false)
    private Instant sendAt;

    public Message(String content) {
        this.content = content;
    }
}
