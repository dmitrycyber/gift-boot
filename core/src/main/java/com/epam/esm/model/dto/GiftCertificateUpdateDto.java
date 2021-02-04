package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateUpdateDto implements Serializable {
    @Null
    private Long id;

    @Size(min = 2, max = 45)
    @NotNull(groups = {CreatingDto.class})
    private JsonNullable<String> name = JsonNullable.undefined();

    @Size(min = 3, max = 255)
    private JsonNullable<String> description = JsonNullable.undefined();

    @Min(value = 1)
    @NotNull(groups = {CreatingDto.class})
    private JsonNullable<Integer> price = JsonNullable.undefined();

    @Min(value = 1)
    @NotNull(groups = {CreatingDto.class})
    private JsonNullable<Integer> duration = JsonNullable.undefined();

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createDate;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @Valid
    private JsonNullable<Set<TagDto>> tags = JsonNullable.undefined();
}
