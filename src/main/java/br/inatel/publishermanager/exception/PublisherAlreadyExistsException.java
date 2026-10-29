package br.inatel.publishermanager.exception;

import br.inatel.publishermanager.model.entity.Publisher;

public class PublisherAlreadyExistsException
extends RuntimeException {
    public PublisherAlreadyExistsException(Publisher publisher) {
        super(String.format("Publisher with id='%s' already exists.", publisher.getId()));
    }
}

