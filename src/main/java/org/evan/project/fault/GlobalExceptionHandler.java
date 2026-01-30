package org.evan.project.fault;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;
import org.evan.project.model.response.ErrorResponse;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {
        String currentPath = uriInfo.getPath();

        if (exception instanceof ResourceNotFoundException ex) {
            return buildResponse(Response.Status.NOT_FOUND, ex.getFault(), currentPath);
        }

        if (exception instanceof InsufficientStockException ex) {
            return buildResponse(Response.Status.BAD_REQUEST, ex.getFault(), currentPath);
        }

        if (exception instanceof ConstraintViolationException) {
            return buildResponse(Response.Status.BAD_REQUEST, ObsFault.INVALID_INPUT, currentPath);
        }

        LOG.error("Unexpected error at " + currentPath, exception);
        return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, ObsFault.GENERAL_ERROR, currentPath);
    }

    private Response buildResponse(Response.Status status, ObsFault fault, String path) {
        return Response.status(status)
                .entity(ErrorResponse.of(fault, path))
                .build();
    }
}
