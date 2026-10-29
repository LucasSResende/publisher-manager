package br.inatel.publishermanager.service;

import br.inatel.publishermanager.mapper.NotificationMapper;
import br.inatel.publishermanager.model.dto.NotificationDTO;
import br.inatel.publishermanager.model.entity.Notification;
import br.inatel.publishermanager.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> saveNotification(NotificationDTO notificationDTO) {
        Notification notification = NotificationMapper.toNotification(notificationDTO);
        this.notificationRepository.save(notification);
        return NotificationMapper.toNotificationDTOList(this.notificationRepository.findAll());
    }

    public List<NotificationDTO> getNotificationList() {
        return NotificationMapper.toNotificationDTOList(this.notificationRepository.findAll());
    }

    public void notificationCallback() {
        List<NotificationDTO> notificationDTOList = NotificationMapper.toNotificationDTOList(this.notificationRepository.findAll());
        HashSet<NotificationDTO> success = new HashSet<>();
        HashSet<NotificationDTO> error = new HashSet<>();
        for (NotificationDTO notificationDTO : notificationDTOList) {
            try {
                String baseUrl = String.format("http://%s:%s", notificationDTO.getHost(), notificationDTO.getPort());
                log.info("Sending callback to {}/publishercache", baseUrl);
                WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
                webClient.delete().uri("/publishercache").retrieve().bodyToMono(Void.class).block();
                success.add(notificationDTO);
            } catch (WebClientException webClientException) {
                error.add(notificationDTO);
                log.debug("Notification error: {}", webClientException.getMessage());
            }
        }
        log.info("Success: {}", success);
        log.info("Error: {}", error);
    }
}
