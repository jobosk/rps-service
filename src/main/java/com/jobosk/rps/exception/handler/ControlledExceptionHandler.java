package com.jobosk.rps.exception.handler;

import com.jobosk.rps.exception.ControlledException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Optional;

@Log4j2
public abstract class ControlledExceptionHandler {

    private static final String ENCODING = "UTF-8";
    private static final String RESPONSE_TYPE = "text/html";
    private static final String GENERIC_MESSAGE = "Unknown exception";

    public static String getMessage(final ControlledException se) {
        return buildMessage(se);
    }

    private static String buildMessage(final ControlledException se) {
        final StringBuilder builder = new StringBuilder();
        if (se != null) {
            builder.append(se.getCode());
            final List<Object> parameters = se.getParams();
            if (parameters != null && !parameters.isEmpty()) {
                builder.append(" |");
                for (final Object p : parameters) {
                    builder.append(" ").append(p);
                }
            }
        }
        return builder.toString();
    }

    public static String getMessage(final Exception exception) {
        return getMessage(exception, true)
                .orElse(GENERIC_MESSAGE);
    }

    private static Optional<String> getMessage(final Exception exception, final boolean firstTime) {
        return Optional.ofNullable(exception)
                .map(e -> Optional.ofNullable(e.getCause())
                        .flatMap(c -> ControlledExceptionHandler.getMessage(c, firstTime))
                        .orElse(e.getMessage())
                );
    }

    public static String getMessage(final Throwable throwable) {
        return getMessage(throwable, true)
                .orElse(GENERIC_MESSAGE);
    }

    private static Optional<String> getMessage(final Throwable throwable, final boolean firstTime) {
        return Optional.ofNullable(throwable)
                .flatMap(t -> {
                    if (t instanceof Exception && firstTime) {
                        return getMessage((Exception) t, false);
                    } else {
                        return Optional.ofNullable(t.getMessage());
                    }
                });
    }

    protected static void alterResponse(final HttpServletResponse response, final int httpStatus, final String message) {
        response.setCharacterEncoding(ENCODING);
        response.setContentType(RESPONSE_TYPE);
        try {
            response.getWriter().append(message);
            response.setStatus(httpStatus);
        } catch (final IOException ioe) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    protected void processControlledException(final ControlledException dre, final HttpServletResponse response) {
        try {
            alterResponse(response, dre.getStatus().value(), getMessage(dre));
        } catch (final Exception exception) {
            log.error("", exception);
            alterResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        }
    }

    protected void processException(final Exception e, final HttpServletResponse response) {
        try {
            alterResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), getMessage(e));
        } catch (final Exception exception) {
            log.error("", exception);
            alterResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        }
    }

    protected void processThrowable(final Throwable t, final HttpServletResponse response) {
        try {
            alterResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), getMessage(t));
        } catch (final Exception exception) {
            log.error("", exception);
            alterResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        }
    }

    @ExceptionHandler(value = ControlledException.class)
    public void handleControlledException(final ControlledException dre, final HttpServletResponse response) {
        log.error("", dre);
        processControlledException(dre, response);
    }

    @ExceptionHandler(value = UndeclaredThrowableException.class)
    public void handleUndeclaredThrowableException(final UndeclaredThrowableException ute,
                                                   final HttpServletResponse response) {
        log.error("", ute);
        final Throwable t = ute.getUndeclaredThrowable();
        if (t instanceof ControlledException) {
            processControlledException((ControlledException) t, response);
        } else {
            processThrowable(t, response);
        }
    }

    @ExceptionHandler(value = Exception.class)
    public void handleException(final Exception e, final HttpServletResponse response) {
        log.error("", e);
        processException(e, response);
    }

    @ExceptionHandler(value = Throwable.class)
    public void handleThrowable(final Throwable t, final HttpServletResponse response) {
        log.error("", t);
        processThrowable(t, response);
    }
}