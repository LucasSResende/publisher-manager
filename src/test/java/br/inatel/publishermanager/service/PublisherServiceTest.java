package br.inatel.publishermanager.service;

import br.inatel.publishermanager.exception.PublisherAlreadyExistsException;
import br.inatel.publishermanager.exception.PublisherNotFoundException;
import br.inatel.publishermanager.model.dto.PublisherDTO;
import br.inatel.publishermanager.model.entity.Publisher;
import br.inatel.publishermanager.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @InjectMocks
    private PublisherService publisherService;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private NotificationService notificationService;

    private Publisher publisher;
    private PublisherDTO publisherDTO;

    @BeforeEach
    void setup() {
        publisher = Publisher.builder()
                .id("nintendo")
                .name("Nintendo Co., Ltd.")
                .build();
        publisherDTO = PublisherDTO.builder()
                .id("nintendo")
                .name("Nintendo Co., Ltd.")
                .build();
    }

    @Test
    @DisplayName("Should successfully save publisher and call notification callback")
    void givenValidPublisher_whenSavePublisher_thenReturnSavedPublisher() {
        when(publisherRepository.existsById("nintendo")).thenReturn(false);
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        PublisherDTO result = publisherService.savePublisher(publisherDTO);

        assertNotNull(result);
        assertEquals("nintendo", result.getId());
        assertEquals("Nintendo Co., Ltd.", result.getName());
        verify(publisherRepository, times(1)).save(any(Publisher.class));
        verify(notificationService, times(1)).notificationCallback();
    }

    @Test
    @DisplayName("Should throw PublisherAlreadyExistsException when publisher id already exists")
    void givenExistingPublisher_whenSavePublisher_thenThrowException() {
        when(publisherRepository.existsById("nintendo")).thenReturn(true);

        assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.savePublisher(publisherDTO));
        verify(publisherRepository, never()).save(any());
        verify(notificationService, never()).notificationCallback();
    }

    @Test
    @DisplayName("Should return publisher by id when found")
    void givenExistingId_whenGetPublisherById_thenReturnListWithPublisher() {
        when(publisherRepository.findById("nintendo")).thenReturn(Optional.of(publisher));

        List<PublisherDTO> result = publisherService.getPublisherById("nintendo");

        assertEquals(1, result.size());
        assertEquals("nintendo", result.get(0).getId());
        verify(publisherRepository, times(1)).findById("nintendo");
    }

    @Test
    @DisplayName("Should return empty list when publisher id is not found")
    void givenNonExistingId_whenGetPublisherById_thenReturnEmptyList() {
        when(publisherRepository.findById("unknown")).thenReturn(Optional.empty());

        List<PublisherDTO> result = publisherService.getPublisherById("unknown");

        assertTrue(result.isEmpty());
        verify(publisherRepository, times(1)).findById("unknown");
    }

    @Test
    @DisplayName("Should return all publishers")
    void whenGetAllPublishers_thenReturnList() {
        when(publisherRepository.findAll()).thenReturn(List.of(publisher));

        List<PublisherDTO> result = publisherService.getAllPublisher();

        assertEquals(1, result.size());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update existing publisher and call notification callback")
    void givenExistingPublisher_whenUpdatePublisher_thenReturnUpdatedPublisher() {
        PublisherDTO updateDTO = PublisherDTO.builder()
                .name("Nintendo Updated")
                .build();
        Publisher updatedPublisher = Publisher.builder()
                .id("nintendo")
                .name("Nintendo Updated")
                .build();

        when(publisherRepository.findById("nintendo")).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(updatedPublisher);

        PublisherDTO result = publisherService.updatePublisher("nintendo", updateDTO);

        assertEquals("Nintendo Updated", result.getName());
        verify(publisherRepository, times(1)).save(any(Publisher.class));
        verify(notificationService, times(1)).notificationCallback();
    }

    @Test
    @DisplayName("Should throw PublisherNotFoundException when updating non-existing publisher")
    void givenNonExistingPublisher_whenUpdatePublisher_thenThrowException() {
        when(publisherRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.updatePublisher("unknown", publisherDTO));
        verify(publisherRepository, never()).save(any());
        verify(notificationService, never()).notificationCallback();
    }

    @Test
    @DisplayName("Should delete existing publisher and call notification callback")
    void givenExistingPublisher_whenDeletePublisher_thenDeleteSuccessfully() {
        when(publisherRepository.existsById("nintendo")).thenReturn(true);

        assertDoesNotThrow(() -> publisherService.deletePublisher("nintendo"));
        verify(publisherRepository, times(1)).deleteById("nintendo");
        verify(notificationService, times(1)).notificationCallback();
    }

    @Test
    @DisplayName("Should throw PublisherNotFoundException when deleting non-existing publisher")
    void givenNonExistingPublisher_whenDeletePublisher_thenThrowException() {
        when(publisherRepository.existsById("unknown")).thenReturn(false);

        assertThrows(PublisherNotFoundException.class, () -> publisherService.deletePublisher("unknown"));
        verify(publisherRepository, never()).deleteById(any());
        verify(notificationService, never()).notificationCallback();
    }
}
