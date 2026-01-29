package org.evan.project.fault;

public class InsufficientStockException extends RuntimeException {

    private static final ObsFault FAULT = ObsFault.INSUFFICIENT_STOCK;

    public InsufficientStockException() {
        super(FAULT.getMessage());
    }

    public ObsFault getFault() {
        return FAULT;
    }
}
