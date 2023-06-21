package br.com.sansys.services.debezium.poc.repository;

import br.com.sansys.services.debezium.poc.entities.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
}