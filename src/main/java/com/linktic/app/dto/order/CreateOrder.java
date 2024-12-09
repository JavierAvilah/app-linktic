package com.linktic.app.dto.order;

import com.linktic.app.dto.detailOrder.CreateDetailOrder;
import com.linktic.app.model.DetailOrder;
import com.linktic.app.model.enumeration.TypeOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateOrder {

    @NotNull
    private TypeOrder typeOrder;
    @NotBlank
    @Size(min = 10)
    private String description;
    @NotEmpty
    private List<CreateDetailOrder> detailOrder;
}
