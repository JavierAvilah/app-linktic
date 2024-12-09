package com.linktic.app.dto.base64data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateBase64Data {

    @NotBlank
    @Pattern(regexp = "\\w+/[-+.\\w]+")
    @Schema(example = "image/jpeg")
    @Size(min = 1, max = 100)
    private String mimeType;

    @NotBlank
    private String data;
}
