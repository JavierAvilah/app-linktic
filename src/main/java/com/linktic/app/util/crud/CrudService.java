package com.linktic.app.util.crud;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.InputStream;
import java.util.List;

public interface CrudService<E, I, C, U, D>{

    D create(@NonNull C dto);
    D update(@NonNull U dto);
    Page<D> find(Specification<E> spec, @NonNull Pageable pageable);
    void delete(@NonNull List<I> idList);
    InputStream generateExportFile();

}
