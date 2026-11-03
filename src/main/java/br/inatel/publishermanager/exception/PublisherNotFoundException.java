package br.inatel.publishermanager.exception;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(String id) {
        super(String.format("Publisher with id='%s' was not found.", id));
    }
}
