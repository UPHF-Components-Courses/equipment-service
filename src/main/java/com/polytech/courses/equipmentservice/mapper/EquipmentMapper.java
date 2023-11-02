package com.polytech.courses.equipmentservice.mapper;

import com.polytech.courses.equipmentservice.api.dto.EquipmentDto;
import com.polytech.courses.equipmentservice.api.response.EquipmentsResponse;
import com.polytech.courses.equipmentservice.constant.EquipmentType;
import com.polytech.courses.equipmentservice.entity.Equipment;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {

    /**
     * Converts a {@link Equipment} to an {@link EquipmentDto}
     *
     * @param equipment the {@link Equipment} to map
     * @return The mapped dto
     */
    public EquipmentDto toDto(Equipment equipment) {
        return EquipmentDto.builder()
            .id(equipment.getId().toHexString())
            .name(equipment.getName())
            .description(equipment.getDescription())
            .picture(equipment.getPicture())
            .type(equipment.getType())
            .build();
    }

    /**
     * Converts several {@link Equipment}s to an {@link EquipmentsResponse}
     *
     * @param equipments The list of {@link Equipment} to map
     * @return The mapped response
     */
    public EquipmentsResponse toResponse(List<Equipment> equipments) {
        final Map<EquipmentType, Long> countByType = equipments.stream()
            .collect(Collectors.groupingBy(Equipment::getType, Collectors.counting()));

        final List<EquipmentDto> dtos = equipments.stream()
            .map(this::toDto)
            .toList();

        return EquipmentsResponse.builder()
            .equipments(dtos)
            .countByType(countByType)
            .build();
    }
}
