package com.linktic.app.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linktic.app.dto.base64data.Base64DataInfoDto;
import com.linktic.app.model.enumeration.StatusProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductDto {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;
    private String createdBy;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;

    private Long id;
    private String name;
    private Double price;
    private String serialNumber;
    private Base64DataInfoDto imagen;
    private String description;
    private StatusProduct statusProduct;
}
