package org.evan.project.fault;


public class ResourceNotFoundException extends RuntimeException {

    private static final ObsFault FAULT = ObsFault.RESOURCE_NOT_FOUND;

    public ResourceNotFoundException() {
        super(FAULT.getMessage());
    }

    public ObsFault getFault() {
        return FAULT;
    }
}
