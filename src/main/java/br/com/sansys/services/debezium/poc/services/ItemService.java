package br.com.sansys.services.debezium.poc.services;


import br.com.sansys.services.debezium.poc.entities.Item;
import br.com.sansys.services.debezium.poc.entities.Outbox;
import br.com.sansys.services.debezium.poc.repository.ItemRepository;
import br.com.sansys.services.debezium.poc.repository.OutboxRepository;
import br.com.sansys.services.debezium.poc.rest.api.CreateItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class ItemService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final ItemRepository itemRepository;

    private final OutboxRepository outboxRepository;

    private final String itemOutboxDestination;

    public ItemService(@Autowired ItemRepository itemRepository,
                       @Autowired OutboxRepository outboxRepository,
                       @Value("${demo.outbox.item.destination}") String itemOutboxDestination) {
        this.itemRepository = itemRepository;
        this.outboxRepository = outboxRepository;
        this.itemOutboxDestination = itemOutboxDestination;
    }


    @Transactional
    public UUID process(CreateItemRequest request) throws Exception {
        Item item = Item.builder()
                .name(request.getName())
                .build();
        item = itemRepository.save(item);
        String outboxPayload = objectMapper.writeValueAsString(item);
        Outbox outboxEvent = Outbox.builder()
                .version("v1")
                .payload(outboxPayload)
                .destination(itemOutboxDestination)
                .timestamp(System.currentTimeMillis())
                .build();
        UUID outboxId = outboxRepository.save(outboxEvent).getId();
        log.info("Item created with id " + item.getId() + " - and Outbox entity created with Id: {}", outboxId);
        return item.getId();
    }
}
