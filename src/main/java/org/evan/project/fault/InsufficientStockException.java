package org.evan.project.fault;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {

    private final ObsFault fault;

    public InsufficientStockException() {
        super("Request must be greater than zero");
        this.fault = ObsFault.INSUFFICIENT_STOCK;
    }

    public InsufficientStockException(ObsFault fault) {
        super(fault.getDefaultMessage());
        this.fault = fault;
    }
}
