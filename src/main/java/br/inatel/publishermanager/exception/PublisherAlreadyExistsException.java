/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.inatel.publishermanager.exception.PublisherAlreadyExistsException
 *  br.inatel.publishermanager.model.entity.Publisher
 */
package br.inatel.publishermanager.exception;

import br.inatel.publishermanager.model.entity.Publisher;

public class PublisherAlreadyExistsException
extends RuntimeException {
    public PublisherAlreadyExistsException(Publisher publisher) {
        super(String.format("Publisher with id='%s' already exists.", publisher.getId()));
    }
}

