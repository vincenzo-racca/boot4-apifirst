package com.vincenzoracca.boot4.config;

import com.vincenzoracca.boot4.exeption.BookDuplicateException;
import com.vincenzoracca.boot4.exeption.BookNotFoundException;
import com.vincenzoracca.boot4.exeption.InvalidSortException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@ControllerAdvice
public class ErrorResponseHandler extends ResponseEntityExceptionHandler {

    private final Log log = LogFactory.getLog(ErrorResponseHandler.class);

    @ExceptionHandler({
            BookNotFoundException.class,
            BookDuplicateException.class,
            InvalidSortException.class
    })
    public ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) {
        log.warn(ex.getMessage());
        int statusCode = 500;
        StringBuilder defaultDetail = new StringBuilder();
        if(ex instanceof BookNotFoundException) {
            statusCode = 404;
            defaultDetail.append("Book not found");
        }

        else if(ex instanceof BookDuplicateException) {
            statusCode = 400;
            defaultDetail.append("Book duplicated");
        }

        else if(ex instanceof InvalidSortException) {
            statusCode = 400;
            defaultDetail.append("Invalid sort");
        }

        HttpStatusCode status = HttpStatusCode.valueOf(statusCode);
        ProblemDetail problem = createProblemDetail(ex, status, defaultDetail.toString(), null,
                null, request);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
}
