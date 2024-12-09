package com.linktic.app.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linktic.app.dto.detailOrder.DetailOrderDto;
import com.linktic.app.model.DetailOrder;
import com.linktic.app.model.enumeration.TypeOrder;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDto {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;
    private String createdBy;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;

    private Long id;
    private String numberOrder;
    private TypeOrder typeOrder;
    private Double total;
    private String description;
}
