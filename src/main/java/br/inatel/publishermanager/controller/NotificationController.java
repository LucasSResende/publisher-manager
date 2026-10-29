package br.inatel.publishermanager.controller;

import br.inatel.publishermanager.model.dto.NotificationDTO;
import br.inatel.publishermanager.service.NotificationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<List<NotificationDTO>> saveNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(this.notificationService.saveNotification(notificationDTO));
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotification() {
        return ResponseEntity.ok(this.notificationService.getNotificationList());
    }
}
