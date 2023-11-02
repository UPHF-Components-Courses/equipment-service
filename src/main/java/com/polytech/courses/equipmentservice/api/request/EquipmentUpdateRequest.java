package com.polytech.courses.equipmentservice.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentUpdateRequest {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String picture;
}
