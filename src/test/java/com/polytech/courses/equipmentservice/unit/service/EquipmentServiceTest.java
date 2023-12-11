package com.polytech.courses.equipmentservice.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.polytech.courses.equipmentservice.api.request.EquipmentCreationRequest;
import com.polytech.courses.equipmentservice.constant.EquipmentType;
import com.polytech.courses.equipmentservice.entity.Equipment;
import com.polytech.courses.equipmentservice.repository.EquipmentRepository;
import com.polytech.courses.equipmentservice.service.EquipmentService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @InjectMocks
    private EquipmentService target;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Test
    void create_should_createAnEquipment_when_validDataProvided() {
        // GIVEN
        final EquipmentCreationRequest request = EquipmentCreationRequest.builder()
            .name("Test")
            .description("Test description")
            .type(EquipmentType.HAT)
            .picture("Test picture")
            .build();

        final ObjectId expectedId = new ObjectId();

        when(equipmentRepository.insert(any(Equipment.class)))
            .then(invoc -> {
                final Equipment input = invoc.getArgument(0);
                input.setId(expectedId);
                return input;
            });

        // WHEN
        final Equipment result = target.create(request);

        // THEN
        assertEquals(expectedId, result.getId());
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getType(), result.getType());
        assertEquals(request.getPicture(), result.getPicture());

        verify(equipmentRepository, times(1))
            .insert(any(Equipment.class));
    }
}
