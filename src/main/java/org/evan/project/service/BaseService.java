package org.evan.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID> {

    T getById(ID id);

    Page<T> getAll(Pageable pageable);

    void delete(ID id);
}
