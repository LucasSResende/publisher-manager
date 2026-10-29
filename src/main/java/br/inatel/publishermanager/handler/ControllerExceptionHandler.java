package br.inatel.publishermanager.handler;

import br.inatel.publishermanager.exception.PublisherAlreadyExistsException;
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
    @Value(value="${server.host}")
    private String serverHost;
    @Value(value="${server.port}")
    private String serverPort;
    @Value(value="${publisher-manager.error.show-stacktrace}")
    private Boolean showStackTrace;

    @ExceptionHandler(value={PublisherAlreadyExistsException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail publisherAlreadyExistsException(PublisherAlreadyExistsException publisherAlreadyExistsException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail((HttpStatusCode)HttpStatus.BAD_REQUEST, (String)this.getErrorMessage((Exception)publisherAlreadyExistsException));
        problemDetail.setTitle("Publisher already exists");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, (Exception)publisherAlreadyExistsException);
        return problemDetail;
    }

    @ExceptionHandler(value={InvalidFormatException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail invalidFormatException(InvalidFormatException invalidFormatException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail((HttpStatusCode)HttpStatus.BAD_REQUEST, (String)this.getErrorMessage((Exception)invalidFormatException));
        problemDetail.setTitle("Invalid information format");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, (Exception)invalidFormatException);
        return problemDetail;
    }

    @ExceptionHandler(value={DatabindException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail jsonMappingException(DatabindException jsonMappingException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail((HttpStatusCode)HttpStatus.BAD_REQUEST, (String)this.getErrorMessage((Exception)jsonMappingException));
        problemDetail.setTitle("Malformed JSON error");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, (Exception)jsonMappingException);
        return problemDetail;
    }

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail((HttpStatusCode)HttpStatus.BAD_REQUEST, (String)this.getErrorMessage((BindException)methodArgumentNotValidException));
        problemDetail.setTitle("Input validation error");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, (Exception)methodArgumentNotValidException);
        return problemDetail;
    }

    @ExceptionHandler(value={JDBCConnectionException.class})
    @ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE)
    public ProblemDetail jDBCConnectionException(JDBCConnectionException jDBCConnectionException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail((HttpStatusCode)HttpStatus.SERVICE_UNAVAILABLE, (String)this.getErrorMessage((Exception)jDBCConnectionException));
        problemDetail.setTitle("Database connection issue");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, (Exception)jDBCConnectionException);
        return problemDetail;
    }

    @ExceptionHandler(value={NullPointerException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail nullPointerException(NullPointerException nullPointerException, HandlerMethod handlerMethod, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail((HttpStatusCode)HttpStatus.INTERNAL_SERVER_ERROR, (String)this.getErrorMessage((Exception)nullPointerException));
        problemDetail.setTitle("");
        problemDetail.setType(URI.create(this.getErrorType()));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        this.setStackTrace(problemDetail, (Exception)nullPointerException);
        return problemDetail;
    }

    private String getErrorMessage(BindException e) {
        return e.getFieldErrors().stream().map(o -> o.toString()).reduce((o1, o2) -> String.format("%s, %s", o1, o2)).get();
    }

    private String getErrorMessage(Exception e) {
        return e.getMessage();
    }

    private void setStackTrace(ProblemDetail problemDetail, Exception e) {
        if (this.showStackTrace.booleanValue()) {
            problemDetail.setProperty("stackTrace", (Object)Arrays.stream(e.getStackTrace()).map(o -> o.toString()).reduce((o1, o2) -> String.format("%s, %s", o1, o2)).get());
        }
    }

    private String getErrorType() {
        return String.format("http://%s:%s/error/types", this.serverHost, this.serverPort);
    }
}

