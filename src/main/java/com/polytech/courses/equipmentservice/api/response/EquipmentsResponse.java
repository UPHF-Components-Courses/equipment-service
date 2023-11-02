package com.polytech.courses.equipmentservice.api.response;

import com.polytech.courses.equipmentservice.api.dto.EquipmentDto;
import com.polytech.courses.equipmentservice.constant.EquipmentType;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentsResponse {

    private List<EquipmentDto> equipments;

    private Map<EquipmentType, Long> countByType;
}
