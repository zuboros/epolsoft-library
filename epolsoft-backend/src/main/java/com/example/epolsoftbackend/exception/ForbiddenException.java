package com.example.epolsoftbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ApiResponse apiResponse;

    public ForbiddenException(ApiResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public ForbiddenException(String message) {
        this(message, null);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
        setApiResponse(message);
    }

    private void setApiResponse(String message) {
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

}
