package com.linktic.app.dto.order;

import com.linktic.app.dto.detailOrder.CreateDetailOrder;
import com.linktic.app.model.enumeration.TypeOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateOrder {
    @NotNull
    private Long id;
    @NotNull
    private JsonNullable<TypeOrder>  typeOrder=JsonNullable.undefined();
    @NotBlank
    @Size(min = 10)
    private JsonNullable<String> description=JsonNullable.undefined();
    private List<Long> idDetailsOrdersDelete;
    private List<CreateDetailOrder> detailOrderAdd;
}
