package com.polytech.courses.equipmentservice.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.polytech.courses.equipmentservice.api.dto.EquipmentDto;
import com.polytech.courses.equipmentservice.api.request.EquipmentCreationRequest;
import com.polytech.courses.equipmentservice.api.response.EquipmentsResponse;
import com.polytech.courses.equipmentservice.constant.EquipmentType;
import com.polytech.courses.equipmentservice.controller.EquipmentController;
import com.polytech.courses.equipmentservice.entity.Equipment;
import com.polytech.courses.equipmentservice.mapper.EquipmentMapper;
import com.polytech.courses.equipmentservice.service.EquipmentService;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class EquipmentControllerTest {

    @InjectMocks
    private EquipmentController target;

    @Mock
    private EquipmentService equipmentService;

    @Mock
    private EquipmentMapper equipmentMapper;

    @Test
    void getAllEquipments_should_returnEquipmentList_when_validRequestProvided() {
        // GIVEN
        // Inputs
        final List<EquipmentType> inputTypes = List.of(EquipmentType.HAT);

        // Expectations
        final Equipment expectedEquipment1 = Equipment.builder()
            .id(new ObjectId())
            .name("Test #1")
            .picture("Test picture")
            .type(EquipmentType.HAT)
            .description("Test description")
            .build();

        final Equipment expectedEquipment2 = Equipment.builder()
            .id(new ObjectId())
            .name("Test #2")
            .picture("Test picture")
            .type(EquipmentType.HAT)
            .description("Test description")
            .build();

        when(equipmentService.getAll(inputTypes))
            .thenReturn(List.of(expectedEquipment1, expectedEquipment2));

        when(equipmentMapper.toResponse(anyList()))
            .thenCallRealMethod();

        when(equipmentMapper.toDto(any()))
            .thenCallRealMethod();

        // WHEN
        final ResponseEntity<EquipmentsResponse> result = target.getAllEquipments(inputTypes);

        // THEN
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final EquipmentsResponse body = result.getBody();
        assertNotNull(body);
        assertEquals(2, body.getEquipments().size());

        final EquipmentDto equipment1 = body.getEquipments().get(0);
        assertEquals(expectedEquipment1.getId().toHexString(), equipment1.getId());
        assertEquals(expectedEquipment1.getName(), equipment1.getName());
        assertEquals(expectedEquipment1.getPicture(), equipment1.getPicture());
        assertEquals(expectedEquipment1.getType(), equipment1.getType());
        assertEquals(expectedEquipment1.getDescription(), equipment1.getDescription());
    }

    @Test
    void createEquipment_should_returnEquipment_when_validRequestProvided() {
        // GIVEN
        // Expectations
        final ObjectId expectedId = new ObjectId();
        final String expectedName = "Test";
        final String expectedDescription = "Test description";
        final EquipmentType expectedType = EquipmentType.WEAPON;
        final String expectedPicture = "Test picture";

        // Inputs
        final EquipmentCreationRequest request = new EquipmentCreationRequest(
            expectedName,
            expectedDescription,
            expectedType,
            expectedPicture
        );

        when(equipmentService.create(any()))
            .thenReturn(Equipment.builder()
                .id(expectedId)
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .picture(request.getPicture())
                .build()
            );

        when(equipmentMapper.toDto(any()))
            .thenCallRealMethod();

        // WHEN
        final ResponseEntity<EquipmentDto> result = target.createEquipment(request);

        // THEN
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        final EquipmentDto body = result.getBody();
        assertNotNull(body);
        assertEquals(expectedId.toHexString(), body.getId());
        assertEquals(expectedName, body.getName());
        assertEquals(expectedPicture, body.getPicture());
        assertEquals(expectedType, body.getType());
        assertEquals(expectedDescription, body.getDescription());
    }

    @Test
    @Disabled("/!\\ Won't work: find out why")
    void createEquipment_should_returnBadRequest_when_requestWithoutNameProvided() {
        // GIVEN
        // Inputs
        final EquipmentCreationRequest request = new EquipmentCreationRequest(
            null,
            "Test description",
            EquipmentType.HAT,
            "Test picture"
        );

        // WHEN
        final ResponseEntity<EquipmentDto> result = target.createEquipment(request);

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
