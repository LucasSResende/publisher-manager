/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.inatel.publishermanager.exception.PublisherAlreadyExistsException
 *  br.inatel.publishermanager.listener.DatabasePopulatorListener
 *  br.inatel.publishermanager.model.dto.PublisherDTO
 *  br.inatel.publishermanager.service.PublisherService
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.event.ApplicationReadyEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.stereotype.Component
 */
package br.inatel.publishermanager.listener;

import br.inatel.publishermanager.exception.PublisherAlreadyExistsException;
import br.inatel.publishermanager.model.dto.PublisherDTO;
import br.inatel.publishermanager.service.PublisherService;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulatorListener
implements ApplicationListener<ApplicationReadyEvent> {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(DatabasePopulatorListener.class);
    private PublisherService publisherService;

    @Autowired
    public DatabasePopulatorListener(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<PublisherDTO> publisherDTOList = Arrays.asList(PublisherDTO.builder().id("sega").name("Sega Corporation").build(), PublisherDTO.builder().id("nintendo").name("Nintendo Co., Ltd.").build());
        publisherDTOList.forEach(publisherDTO -> {
            try {
                this.publisherService.savePublisher(publisherDTO);
            }
            catch (PublisherAlreadyExistsException e) {
                log.info("Publisher with id='{}' already exists.", (Object)publisherDTO.getId());
            }
        });
    }
}

