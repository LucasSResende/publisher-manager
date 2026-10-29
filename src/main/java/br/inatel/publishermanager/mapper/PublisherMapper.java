/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.inatel.publishermanager.mapper.PublisherMapper
 *  br.inatel.publishermanager.model.dto.PublisherDTO
 *  br.inatel.publishermanager.model.entity.Publisher
 */
package br.inatel.publishermanager.mapper;

import br.inatel.publishermanager.model.dto.PublisherDTO;
import br.inatel.publishermanager.model.entity.Publisher;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherMapper {
    public static List<Publisher> toPublisherList(List<PublisherDTO> publisherDTOList) {
        return publisherDTOList.stream().map(PublisherMapper::toPublisher).collect(Collectors.toList());
    }

    public static List<PublisherDTO> toPublisherDTOList(List<Publisher> publisherList) {
        return publisherList.stream().map(PublisherMapper::toPublisherDTO).collect(Collectors.toList());
    }

    public static Publisher toPublisher(PublisherDTO publisherDTO) {
        return Publisher.builder().id(publisherDTO.getId()).name(publisherDTO.getName()).build();
    }

    public static PublisherDTO toPublisherDTO(Publisher publisher) {
        return PublisherDTO.builder().id(publisher.getId()).name(publisher.getName()).build();
    }
}

