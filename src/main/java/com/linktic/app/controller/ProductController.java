package com.linktic.app.controller;

import com.linktic.app.dto.product.CreateProduct;
import com.linktic.app.dto.product.ProductDto;
import com.linktic.app.dto.product.UpdateProduct;
import com.linktic.app.model.Product;
import com.linktic.app.service.ProductService;
import com.linktic.app.util.crud.CruController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Product")
@RequestMapping("api/product")
@SecurityRequirement(name = "JWT_Token")
public class ProductController extends CruController<Product,Long, CreateProduct, UpdateProduct, ProductDto> {

    protected ProductController(ProductService service) {super(service);}
}
