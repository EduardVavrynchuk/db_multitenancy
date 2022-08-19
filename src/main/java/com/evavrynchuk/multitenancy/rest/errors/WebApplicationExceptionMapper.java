package com.evavrynchuk.multitenancy.rest.errors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    public WebApplicationExceptionMapper() {
        super();
    }

    @Override
    public Response toResponse(WebApplicationException exception) {

        LOGGER.info(exception.getResponse().getStatus() + ": " + exception.getMessage());
        LOGGER.debug("uncaught exception: ", exception);

        return Response
                .status(exception.getResponse().getStatus())
                .type(MediaType.TEXT_PLAIN)
                .entity(exception.getMessage())
                .build();
    }
}
