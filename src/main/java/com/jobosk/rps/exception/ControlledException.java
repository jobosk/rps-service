package com.jobosk.rps.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class ControlledException extends RuntimeException {

    private static final long serialVersionUID = -5236165791193935267L;

    private HttpStatus status;
    private String code;
    private List<Object> params;
    private RuntimeException originalException;

    public ControlledException(final HttpStatus status, final String code
            , final List<Object> params, final RuntimeException originalException) {
        this.status = status;
        this.code = code;
        this.params = params;
        this.originalException = originalException;
    }

    public ControlledException(final String code, final List<Object> params, final RuntimeException originalException) {
        this(HttpStatus.BAD_REQUEST, code, params, originalException);
    }

    public ControlledException(final String code, final RuntimeException originalException) {
        this(code, new ArrayList<>(), originalException);
    }

    public ControlledException(final HttpStatus status, final String code, final List<Object> params) {
        this(status, code, params, null);
    }

    public ControlledException(final String code, final List<Object> params) {
        this(code, params, null);
    }

    public ControlledException(final HttpStatus status, final String code) {
        this(status, code, new ArrayList<>(), null);
    }

    public ControlledException(final String code) {
        this(code, new ArrayList<>(), null);
    }

    public ControlledException() {
        this("unknown_error");
    }
}
