package com.linktic.app.service;

import com.linktic.app.dto.product.CreateProduct;
import com.linktic.app.dto.product.ProductDto;
import com.linktic.app.dto.product.UpdateProduct;
import com.linktic.app.exception.EntityNotFoundException;
import com.linktic.app.exception.ExportFileError;
import com.linktic.app.model.Base64Data;
import com.linktic.app.model.Product;
import com.linktic.app.model.enumeration.Accessibility;
import com.linktic.app.model.enumeration.StatusProduct;
import com.linktic.app.repository.ProductRepository;
import com.linktic.app.util.Converter;
import com.linktic.app.util.ExportFileXlsx;
import com.linktic.app.util.SpecificationUtils;
import com.linktic.app.util.Validator;
import com.linktic.app.util.crud.CrudService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ProductService implements CrudService<Product,Long, CreateProduct, UpdateProduct, ProductDto> {
    @Autowired
    private Validator validator;
    @Autowired
    private Converter converter;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public ProductDto create(@NonNull CreateProduct dto) {

        validator.validate(dto);
        Base64Data imagen = null;
        if(dto.getImagen()!=null){
            validator.validate(dto.getImagen());
            imagen = new Base64Data();
            imagen.setBase64(dto.getImagen().getData());
            imagen.setMimeType(dto.getImagen().getMimeType());
            imagen.setAccessibility(Accessibility.PUBLIC);
        }

        Product product=Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .serialNumber(dto.getSerialNumber())
                .description(dto.getDescription())
                .statusProduct(StatusProduct.AVAILABLE)
                .imagen(imagen)
                .build();

        productRepository.save(product);
        return converter.map(product,ProductDto.class);
    }

    @Override
    public ProductDto update(@NonNull UpdateProduct dto) {

        Product product=productRepository.findById(dto.getId())
                .orElseThrow(()-> new EntityNotFoundException(Product.class,dto.getId()));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStatusProduct(dto.getStatusProduct());
        Base64Data imagen = null;
        if(dto.getImagen()!=null){
            validator.validate(dto.getImagen());
            imagen = new Base64Data();
            imagen.setBase64(dto.getImagen().getData());
            imagen.setMimeType(dto.getImagen().getMimeType());
            imagen.setAccessibility(Accessibility.PUBLIC);
        }
        product.setImagen(imagen);
        productRepository.save(product);

        return converter.map(product,ProductDto.class);
    }

    @Override
    public Page<ProductDto> find(Specification<Product> spec, @NonNull Pageable pageable) {
        return converter.mapPage(productRepository.findAll(SpecificationUtils.distinct(spec),pageable), ProductDto.class);
    }

    @Override
    public void delete(@NonNull List<Long> idList) {
        idList.stream().forEach(id->{productRepository.deleteById(id);});
    }

    @Override
    public InputStream generateExportFile() {
        List<Product>bodegas=productRepository.findAll();
        try {
            return ExportFileXlsx.createExport(bodegas);
        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            throw new ExportFileError(e.getMessage());}
    }
}
