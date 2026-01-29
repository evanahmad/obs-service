package org.evan.project.model.response;

import java.time.ZonedDateTime;

public record ApiResponse<T>(
        T data,
        ZonedDateTime timestamp
) {
    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(
                data,
                ZonedDateTime.now()
        );
    }
}
