package com.linktic.app.dto.product;

import com.linktic.app.dto.base64data.CreateBase64Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateProduct {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @DecimalMin("0.0")
    private Double price;
    private CreateBase64Data imagen;
    @NotBlank
    @Size(min = 10, max = 10)
    private String serialNumber;
    @NotBlank
    @Size(min = 10)
    private String description;

}
