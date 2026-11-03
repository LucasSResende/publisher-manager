package br.inatel.publishermanager.controller;

import br.inatel.publishermanager.exception.PublisherNotFoundException;
import br.inatel.publishermanager.model.dto.PublisherDTO;
import br.inatel.publishermanager.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublisherController Unit Tests")
class PublisherControllerTest {

    @InjectMocks
    private PublisherController publisherController;

    @Mock
    private PublisherService publisherService;

    private PublisherDTO publisherDTO;

    @BeforeEach
    void setup() {
        publisherDTO = PublisherDTO.builder()
                .id("nintendo")
                .name("Nintendo Co., Ltd.")
                .build();
    }

    @Test
    @DisplayName("Return 200 and list of publishers on GET /publisher without id")
    void whenGetPublisherWithoutId_thenReturnAll() {
        when(publisherService.getAllPublisher()).thenReturn(List.of(publisherDTO));

        ResponseEntity<List<PublisherDTO>> response = publisherController.getPublisher(Optional.empty());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("nintendo", response.getBody().get(0).getId());
        verify(publisherService).getAllPublisher();
    }

    @Test
    @DisplayName("Return 200 and publisher on GET /publisher with query param id")
    void whenGetPublisherWithIdParam_thenReturnPublisher() {
        when(publisherService.getPublisherById("nintendo")).thenReturn(List.of(publisherDTO));

        ResponseEntity<List<PublisherDTO>> response = publisherController.getPublisher(Optional.of("nintendo"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(publisherService).getPublisherById("nintendo");
    }

    @Test
    @DisplayName("Return 200 on GET /publisher/{id}")
    void whenGetPublisherById_thenReturnPublisher() {
        when(publisherService.getPublisherById("nintendo")).thenReturn(List.of(publisherDTO));

        ResponseEntity<PublisherDTO> response = publisherController.getPublisherById("nintendo");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("nintendo", response.getBody().getId());
        verify(publisherService).getPublisherById("nintendo");
    }

    @Test
    @DisplayName("Throw PublisherNotFoundException on GET /publisher/{id} when not found")
    void whenGetPublisherByIdNotFound_thenThrowException() {
        when(publisherService.getPublisherById("unknown")).thenReturn(List.of());

        assertThrows(PublisherNotFoundException.class, () -> publisherController.getPublisherById("unknown"));
        verify(publisherService).getPublisherById("unknown");
    }

    @Test
    @DisplayName("Return 201 on POST /publisher")
    void whenSavePublisher_thenReturnCreated() {
        when(publisherService.savePublisher(publisherDTO)).thenReturn(publisherDTO);

        ResponseEntity<PublisherDTO> response = publisherController.savePublisher(publisherDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("nintendo", response.getBody().getId());
        verify(publisherService).savePublisher(publisherDTO);
    }

    @Test
    @DisplayName("Return 200 on PUT /publisher/{id}")
    void whenUpdatePublisher_thenReturnOk() {
        PublisherDTO updated = PublisherDTO.builder()
                .id("nintendo")
                .name("Nintendo Updated")
                .build();
        when(publisherService.updatePublisher("nintendo", updated)).thenReturn(updated);

        ResponseEntity<PublisherDTO> response = publisherController.updatePublisher("nintendo", updated);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nintendo Updated", response.getBody().getName());
        verify(publisherService).updatePublisher("nintendo", updated);
    }

    @Test
    @DisplayName("Return 204 on DELETE /publisher/{id}")
    void whenDeletePublisher_thenReturnNoContent() {
        doNothing().when(publisherService).deletePublisher("nintendo");

        ResponseEntity<Void> response = publisherController.deletePublisher("nintendo");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(publisherService).deletePublisher("nintendo");
    }
}
