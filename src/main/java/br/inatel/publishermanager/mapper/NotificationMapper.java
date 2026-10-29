/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.inatel.publishermanager.mapper.NotificationMapper
 *  br.inatel.publishermanager.model.dto.NotificationDTO
 *  br.inatel.publishermanager.model.entity.Notification
 */
package br.inatel.publishermanager.mapper;

import br.inatel.publishermanager.model.dto.NotificationDTO;
import br.inatel.publishermanager.model.entity.Notification;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
    public static List<Notification> toNotificationList(List<NotificationDTO> notificationDTOList) {
        return notificationDTOList.stream().map(NotificationMapper::toNotification).collect(Collectors.toList());
    }

    public static List<NotificationDTO> toNotificationDTOList(List<Notification> notificationList) {
        return notificationList.stream().map(NotificationMapper::toNotificationDTO).collect(Collectors.toList());
    }

    public static Notification toNotification(NotificationDTO notificationDTO) {
        return Notification.builder().hostAndPort(String.format("%s:%s", notificationDTO.getHost(), notificationDTO.getPort())).build();
    }

    public static NotificationDTO toNotificationDTO(Notification notification) {
        return NotificationDTO.builder().host(notification.getHostAndPort().split(":")[0]).port(notification.getHostAndPort().split(":")[1]).build();
    }
}

