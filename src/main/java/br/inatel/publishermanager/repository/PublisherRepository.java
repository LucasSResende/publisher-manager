package br.inatel.publishermanager.repository;

import br.inatel.publishermanager.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository
extends JpaRepository<Publisher, String> {
}

