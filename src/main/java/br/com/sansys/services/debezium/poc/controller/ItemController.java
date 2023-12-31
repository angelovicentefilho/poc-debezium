package br.com.sansys.services.debezium.poc.controller;


import br.com.sansys.services.debezium.poc.rest.api.CreateItemRequest;
import br.com.sansys.services.debezium.poc.services.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> createItem(@RequestBody CreateItemRequest request) {
        log.info("Received request to create item with name: " + request.getName());
        try {
            UUID itemId = itemService.process(request);
            return ResponseEntity.created(URI.create(itemId.toString())).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}