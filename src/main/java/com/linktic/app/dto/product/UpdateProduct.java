package com.linktic.app.dto.product;


import com.linktic.app.dto.base64data.UpdateBase64Data;
import com.linktic.app.model.enumeration.StatusProduct;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateProduct {
    @NotNull
    private Long id;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @DecimalMin("0.0")
    private Double price;
    private UpdateBase64Data imagen;
    @NotBlank
    @Size(min = 10)
    private String description;
    @NotNull
    private StatusProduct statusProduct;
}
