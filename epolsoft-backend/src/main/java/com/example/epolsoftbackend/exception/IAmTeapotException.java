package com.example.epolsoftbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class IAmTeapotException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ApiResponse apiResponse;

    public IAmTeapotException(ApiResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public IAmTeapotException(String message) {
        this(message, null);
    }

    public IAmTeapotException(String message, Throwable cause) {
        super(message, cause);
        setApiResponse(message);
    }

    private void setApiResponse(String message) {
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

}
