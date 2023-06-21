package br.com.sansys.services.debezium.poc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity(name = "Outbox")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Outbox {

    public static final int VARCHAR_MAX_LENGTH = 4096;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false, length = VARCHAR_MAX_LENGTH)
    private String payload;

    @Column(nullable = false)
    private long timestamp;

    @Column(nullable = false)
    private String destination;

    private String version;
}