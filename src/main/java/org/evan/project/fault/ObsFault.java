package org.evan.project.fault;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ObsFault {

    GENERAL_ERROR(
            "GNR_ERROR_000",
            "An unexpected internal server error occurred"
    ),

    RESOURCE_NOT_FOUND(
            "RES_ERROR_404",
            "The requested resource was not found"
    ),

    INVALID_INPUT(
            "REQ_ERROR_400",
            "The request contains invalid or malformed input"
    ),

    INSUFFICIENT_STOCK(
            "INV_ERROR_409",
            "Insufficient stock to fulfill the requested quantity"
    );

    private final String code;
    private final String defaultMessage;
}
