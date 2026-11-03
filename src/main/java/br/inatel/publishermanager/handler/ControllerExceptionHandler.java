package br.inatel.publishermanager.handler;

import br.inatel.publishermanager.exception.PublisherAlreadyExistsException;
import br.inatel.publishermanager.exception.PublisherNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import lombok.Generated;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    @Value(value="${server.host:localhost}")
    private String serverHost;
    @Value(value="${server.port:8080}")
    private String serverPort;
    @Value(value="${publisher-manager.error.show-stacktrace:false}")
    private Boolean showStackTrace;

    @ExceptionHandler(value={PublisherAlreadyExistsException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail publisherAlreadyExistsException(PublisherAlreadyExistsException publisherAlreadyExistsException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, this.getErrorMessage(publisherAlreadyExistsException));
        problemDetail.setTitle("Publisher already exists");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, publisherAlreadyExistsException);
        return problemDetail;
    }

    @ExceptionHandler(value={PublisherNotFoundException.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ProblemDetail publisherNotFoundException(PublisherNotFoundException publisherNotFoundException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, this.getErrorMessage(publisherNotFoundException));
        problemDetail.setTitle("Publisher not found");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, publisherNotFoundException);
        return problemDetail;
    }

    @ExceptionHandler(value={InvalidFormatException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail invalidFormatException(InvalidFormatException invalidFormatException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, this.getErrorMessage(invalidFormatException));
        problemDetail.setTitle("Invalid information format");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, invalidFormatException);
        return problemDetail;
    }

    @ExceptionHandler(value={DatabindException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail jsonMappingException(DatabindException jsonMappingException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, this.getErrorMessage(jsonMappingException));
        problemDetail.setTitle("Malformed JSON error");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, jsonMappingException);
        return problemDetail;
    }

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, this.getErrorMessage(methodArgumentNotValidException));
        problemDetail.setTitle("Input validation error");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, methodArgumentNotValidException);
        return problemDetail;
    }

    @ExceptionHandler(value={JDBCConnectionException.class})
    @ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE)
    public ProblemDetail jDBCConnectionException(JDBCConnectionException jDBCConnectionException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, this.getErrorMessage(jDBCConnectionException));
        problemDetail.setTitle("Database connection issue");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, jDBCConnectionException);
        return problemDetail;
    }

    @ExceptionHandler(value={NullPointerException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail nullPointerException(NullPointerException nullPointerException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, this.getErrorMessage(nullPointerException));
        problemDetail.setTitle("Null pointer exception");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, nullPointerException);
        return problemDetail;
    }

    private String getErrorMessage(BindException e) {
        return e.getFieldErrors().stream().map(o -> o.toString()).reduce((o1, o2) -> String.format("%s, %s", o1, o2)).get();
    }

    private String getErrorMessage(Exception e) {
        return e.getMessage();
    }

    private void setStackTrace(ProblemDetail problemDetail, Exception e) {
        if (this.showStackTrace != null && this.showStackTrace) {
            problemDetail.setProperty("stackTrace", Arrays.stream(e.getStackTrace()).map(o -> o.toString()).reduce((o1, o2) -> String.format("%s, %s", o1, o2)).get());
        }
    }

    private String getErrorType() {
        return String.format("http://%s:%s/error/types", this.serverHost, this.serverPort);
    }
}
