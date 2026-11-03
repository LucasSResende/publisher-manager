package br.inatel.publishermanager.controller;

import br.inatel.publishermanager.exception.PublisherNotFoundException;
import br.inatel.publishermanager.model.dto.PublisherDTO;
import br.inatel.publishermanager.service.PublisherService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getPublisher(@RequestParam(required = false) Optional<String> id) {
        List<PublisherDTO> publisherDTOList = id.isPresent()
                ? this.publisherService.getPublisherById(id.get())
                : this.publisherService.getAllPublisher();
        return ResponseEntity.ok(publisherDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable String id) {
        return this.publisherService.getPublisherById(id).stream()
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> savePublisher(@Valid @RequestBody PublisherDTO publisherDTO) {
        return new ResponseEntity<>(this.publisherService.savePublisher(publisherDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> updatePublisher(@PathVariable String id, @Valid @RequestBody PublisherDTO publisherDTO) {
        return ResponseEntity.ok(this.publisherService.updatePublisher(id, publisherDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable String id) {
        this.publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
