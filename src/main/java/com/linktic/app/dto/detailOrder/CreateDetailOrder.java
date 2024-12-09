package com.linktic.app.dto.detailOrder;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateDetailOrder {

    @NotNull
    @Min(1)
    @Max(10)
    private Integer quantity;
    @NotNull
    private Long productId;
}
