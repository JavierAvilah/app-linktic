package com.linktic.app.dto.detailOrder;

import com.linktic.app.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DetailOrderDto {

    private Long id;
    private Integer quantity;
    private ProductDto product;
}
