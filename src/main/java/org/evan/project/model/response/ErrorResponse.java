package org.evan.project.model.response;

import org.evan.project.fault.ObsFault;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public record ErrorResponse(
        String errorCode,
        String errorMessage,
        String path,
        String timestamp
) {
    public static ErrorResponse of(
            ObsFault fault,
            String path
    ) {
        return new ErrorResponse(
                fault.getCode(),
                fault.getMessage(),
                path,
                ZonedDateTime.now()
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
    }
}
