/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.inatel.publishermanager.model.entity.Publisher
 *  br.inatel.publishermanager.repository.PublisherRepository
 *  org.springframework.data.jpa.repository.JpaRepository
 */
package br.inatel.publishermanager.repository;

import br.inatel.publishermanager.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository
extends JpaRepository<Publisher, String> {
}

