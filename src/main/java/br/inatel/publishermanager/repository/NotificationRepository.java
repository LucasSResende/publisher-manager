package br.inatel.publishermanager.repository;

import br.inatel.publishermanager.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
extends JpaRepository<Notification, String> {
}

