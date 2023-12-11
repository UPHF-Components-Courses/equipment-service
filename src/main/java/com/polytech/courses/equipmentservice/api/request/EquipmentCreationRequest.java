package com.polytech.courses.equipmentservice.api.request;

import com.polytech.courses.equipmentservice.constant.EquipmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentCreationRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private EquipmentType type;

    @NotBlank
    private String picture;
}
