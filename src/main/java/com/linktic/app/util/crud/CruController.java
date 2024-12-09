package com.linktic.app.util.crud;

import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

public abstract class CruController<E, I, C, U, D> {

    private CrudService<E, I, C, U, D> service;


    protected CruController(CrudService<E, I, C, U, D> service){
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new entry of this entity.")
    public D create(@RequestBody C create){
        return service.create(create);
    }

    @GetMapping("find")
    @Operation(summary = "Find entries of this entity using search filters.")
    public Page<D> find(@Parameter(hidden = true) @Filter Specification<E> spec
            , @Parameter(hidden = true) Pageable pageable
            , @RequestParam(required = false) String filter, @RequestParam(required = false) Integer page
            , @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort){
        return service.find(spec, pageable);
    }

    @PatchMapping
    @Operation(summary = "Update values of an entry of this entity.")
    public D update(@RequestBody U update){
        return service.update(update);
    }


    @DeleteMapping
    @Operation(summary = "Delete entries from entity")
    public void delete(@RequestBody List<I> idList){service.delete(idList);}

    @GetMapping("export")
    @Operation(summary = "Export entity data.xlsx")
    public ResponseEntity<InputStreamResource> exportArticulosToExcel() {

        InputStream excelStream = service.generateExportFile();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=data.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(excelStream));

    }
}
