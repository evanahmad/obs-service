package org.evan.project.fault;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ObsFault fault;

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(String.format(
                "%s not found with identifier: %s",
                resourceName,
                identifier
        ));
        this.fault = ObsFault.RESOURCE_NOT_FOUND;
    }

    public ResourceNotFoundException(ObsFault fault) {
        super(fault.getDefaultMessage());
        this.fault = fault;
    }
}
