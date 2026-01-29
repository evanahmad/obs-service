package org.evan.project.util;

import org.evan.project.fault.InsufficientStockException;

public final class QuantityValidator {

    private QuantityValidator() {}

    public static void validatePositive(int qty) {
        if (qty <= 0) {
            throw new InsufficientStockException();
        }
    }
}
