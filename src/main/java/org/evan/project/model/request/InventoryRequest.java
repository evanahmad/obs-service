package org.evan.project.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.evan.project.model.enums.InventoryType;

public record InventoryRequest(
        @NotNull(message = "itemId is required")
        Long itemId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        @NotNull(message = "type is required")
        InventoryType type
) {
}
