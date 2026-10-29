/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.inatel.publishermanager.model.entity.Notification
 *  br.inatel.publishermanager.repository.NotificationRepository
 *  org.springframework.data.jpa.repository.JpaRepository
 */
package br.inatel.publishermanager.repository;

import br.inatel.publishermanager.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
extends JpaRepository<Notification, String> {
}

