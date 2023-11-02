package com.polytech.courses.equipmentservice.service;

import com.polytech.courses.equipmentservice.api.request.EquipmentCreationRequest;
import com.polytech.courses.equipmentservice.api.request.EquipmentUpdateRequest;
import com.polytech.courses.equipmentservice.constant.EquipmentType;
import com.polytech.courses.equipmentservice.entity.Equipment;
import com.polytech.courses.equipmentservice.repository.EquipmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    /**
     * Gets every known {@link Equipment}
     *
     * @param types A list of types to filter on. Ignore if null or empty provided.
     * @return A list of known {@link Equipment}s
     */
    public List<Equipment> getAll(List<EquipmentType> types) {
        if (CollectionUtils.isEmpty(types)) {
            return equipmentRepository.findAll();
        } else {
            return equipmentRepository.findAllByTypeIn(types);
        }
    }

    /**
     * Gets an {@link Equipment} from its id
     *
     * @param equipmentId The id of the desired {@link Equipment}
     * @return The matching {@link Equipment} or null if it doesn't exist
     */
    public Equipment getById(String equipmentId) {
        return equipmentRepository.findById(new ObjectId(equipmentId))
            .orElse(null);
    }

    /**
     * Creates an {@link Equipment} from a request
     *
     * @param request The request containing {@link Equipment}'s data
     * @return The created {@link Equipment}
     */
    public Equipment create(EquipmentCreationRequest request) {
        final Equipment equipment = Equipment.builder()
            .name(request.getName())
            .description(request.getDescription())
            .type(request.getType())
            .picture(request.getPicture())
            .build();

        return equipmentRepository.insert(equipment);
    }

    /**
     * Updates an {@link Equipment} from a request
     *
     * @param equipmentId The id of the {@link Equipment} to update
     * @param request     The request containing {@link Equipment}'s data to update
     * @return The updated {@link Equipment}
     */
    public Equipment update(String equipmentId, EquipmentUpdateRequest request) {
        return equipmentRepository.findById(new ObjectId(equipmentId))
            .map(equipment -> {
                equipment.setName(request.getName());
                equipment.setDescription(request.getDescription());
                equipment.setPicture(request.getPicture());

                return equipmentRepository.save(equipment);
            })
            .orElse(null);
    }

    /**
     * Deletes the {@link Equipment} with the provided id
     *
     * @param equipmentId The id of the {@link Equipment} to delete
     */
    public void delete(String equipmentId) {
        equipmentRepository.deleteById(new ObjectId(equipmentId));
    }
}
