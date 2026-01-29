package org.evan.project.model.response;

public record ErrorResponse(
        String errorCode,
        String errorMessage,
        String path,
        String time
) {
}
