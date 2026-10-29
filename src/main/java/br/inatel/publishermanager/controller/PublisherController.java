package br.inatel.publishermanager.controller;

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

    @PostMapping
    public ResponseEntity<PublisherDTO> savePublisher(@Valid @RequestBody PublisherDTO publisherDTO) {
        return new ResponseEntity<>(this.publisherService.savePublisher(publisherDTO), HttpStatus.CREATED);
    }
}
