package br.com.sansys.services.debezium.poc.repository;

import br.com.sansys.services.debezium.poc.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}