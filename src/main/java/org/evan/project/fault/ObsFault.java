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
            "RES_NOT_FOUND_001",
            "The requested resource does not exist"
    ),

    INVALID_INPUT(
            "REQ_INVALID_002",
            "Request input validation failed"
    ),

    INSUFFICIENT_STOCK(
            "INV_STOCK_003",
            "Insufficient stock to fulfill the requested quantity"
    );

    private final String code;
    private final String message;
}
