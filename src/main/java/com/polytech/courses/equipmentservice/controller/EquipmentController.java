package com.polytech.courses.equipmentservice.controller;

import com.polytech.courses.equipmentservice.api.dto.EquipmentDto;
import com.polytech.courses.equipmentservice.api.request.EquipmentCreationRequest;
import com.polytech.courses.equipmentservice.api.request.EquipmentUpdateRequest;
import com.polytech.courses.equipmentservice.api.response.EquipmentsResponse;
import com.polytech.courses.equipmentservice.constant.EquipmentType;
import com.polytech.courses.equipmentservice.entity.Equipment;
import com.polytech.courses.equipmentservice.mapper.EquipmentMapper;
import com.polytech.courses.equipmentservice.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipments")
@Slf4j
@RequiredArgsConstructor
@Tag(
    name = "Equipment controller",
    description = "APIs used to manage equipments"
)
public class EquipmentController {

    private final EquipmentService equipmentService;

    private final EquipmentMapper equipmentMapper;

    @GetMapping
    @Operation(
        summary = "Get all equipments",
        description = "Get every equipments that matches provided filters.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Response in case of success",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = EquipmentsResponse.class))
            ),
        }
    )
    public ResponseEntity<EquipmentsResponse> getAllEquipments(
        @Parameter(description = "A list of equipment types to filter on. The filter will not be applied if none provided.")
        @RequestParam(required = false)
        List<EquipmentType> types
    ) {
        final List<Equipment> equipments = equipmentService.getAll(types);
        final EquipmentsResponse response = equipmentMapper.toResponse(equipments);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{equipmentId}")
    @Operation(
        summary = "Get an equipment by id",
        description = "Get the equipment with the provided id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Response in case of success",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = EquipmentDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No equipment is matching the provided id"
            )
        }
    )
    public ResponseEntity<EquipmentDto> getEquipment(
        @Parameter(description = "The id of the equipment", example = "653280875702666aaa785071")
        @PathVariable
        String equipmentId
    ) {
        final Equipment equipment = equipmentService.getById(equipmentId);

        return equipment != null
            ? ResponseEntity.ok(equipmentMapper.toDto(equipment))
            : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Create an equipment",
        description = "Create an equipment from the provided data.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Response if the equipment has been successfully created",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = EquipmentDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Response if the provided data is not valid",
                content = @Content(mediaType = "application/json")
            )
        }
    )
    public ResponseEntity<EquipmentDto> createEquipment(@Valid @RequestBody EquipmentCreationRequest request) {
        final Equipment equipment = equipmentService.create(request);
        final EquipmentDto dto = equipmentMapper.toDto(equipment);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{equipmentId}")
    @GetMapping("/{equipmentId}")
    @Operation(
        summary = "Update an equipment by id",
        description = "Update the equipment with the provided id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Response in case of success",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = EquipmentDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Response if the provided data is not valid",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No equipment is matching the provided id"
            )
        }
    )
    public ResponseEntity<EquipmentDto> updateEquipment(
        @PathVariable
        @Parameter(description = "The id of the equipment")
        String equipmentId,

        @Valid
        @RequestBody
        EquipmentUpdateRequest request
    ) {
        final Equipment equipment = equipmentService.update(equipmentId, request);

        return equipment != null
            ? ResponseEntity.ok(equipmentMapper.toDto(equipment))
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{equipmentId}")
    @GetMapping("/{equipmentId}")
    @Operation(
        summary = "Delete an equipment by id",
        description = "Delete the equipment with the provided id.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Response if the equipment has been successfully deleted"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No equipment is matching the provided id"
            )
        }
    )
    public ResponseEntity<Void> deleteEquipment(
        @Parameter(description = "The id of the equipment to delete")
        @PathVariable
        String equipmentId
    ) {
        equipmentService.delete(equipmentId);

        return ResponseEntity.accepted().build();
    }
}
