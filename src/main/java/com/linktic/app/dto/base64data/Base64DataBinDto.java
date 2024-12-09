package com.linktic.app.dto.base64data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linktic.app.model.enumeration.Accessibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Base64DataBinDto {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;
    private String createdBy;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;


    private UUID id;
    private String mimeType;
    private Integer length;
    private Accessibility accessibility;
    private byte[] data;
}