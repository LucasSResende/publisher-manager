package br.inatel.publishermanager.service;

import br.inatel.publishermanager.exception.PublisherAlreadyExistsException;
import br.inatel.publishermanager.exception.PublisherNotFoundException;
import br.inatel.publishermanager.mapper.PublisherMapper;
import br.inatel.publishermanager.model.dto.PublisherDTO;
import br.inatel.publishermanager.model.entity.Publisher;
import br.inatel.publishermanager.repository.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final NotificationService notificationService;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, NotificationService notificationService) {
        this.publisherRepository = publisherRepository;
        this.notificationService = notificationService;
    }

    public PublisherDTO savePublisher(PublisherDTO publisherDTO) {
        Publisher publisher = PublisherMapper.toPublisher(publisherDTO);
        if (!this.publisherRepository.existsById(publisher.getId())) {
            Publisher saved = this.publisherRepository.save(publisher);
            this.notificationService.notificationCallback();
            return PublisherMapper.toPublisherDTO(saved);
        }
        throw new PublisherAlreadyExistsException(publisher);
    }

    public List<PublisherDTO> getPublisherById(String id) {
        Optional<Publisher> publisherOptional = this.publisherRepository.findById(id);
        if (publisherOptional.isPresent()) {
            return PublisherMapper.toPublisherDTOList(List.of(publisherOptional.get()));
        }
        return Collections.emptyList();
    }

    public List<PublisherDTO> getAllPublisher() {
        return PublisherMapper.toPublisherDTOList(this.publisherRepository.findAll());
    }

    public PublisherDTO updatePublisher(String id, PublisherDTO publisherDTO) {
        Publisher existing = this.publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
        existing.setName(publisherDTO.getName());
        Publisher saved = this.publisherRepository.save(existing);
        this.notificationService.notificationCallback();
        return PublisherMapper.toPublisherDTO(saved);
    }

    public void deletePublisher(String id) {
        if (!this.publisherRepository.existsById(id)) {
            throw new PublisherNotFoundException(id);
        }
        this.publisherRepository.deleteById(id);
        this.notificationService.notificationCallback();
    }
}
