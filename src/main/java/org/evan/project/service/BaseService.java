package org.evan.project.service;

import java.util.List;

public interface BaseService<T, ID> {
    T getById(ID id);
    List<T> getAll(int page, int size);
    void delete(ID id);
}
