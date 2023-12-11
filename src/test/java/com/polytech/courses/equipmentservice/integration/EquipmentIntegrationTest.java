package com.polytech.courses.equipmentservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytech.courses.equipmentservice.api.request.EquipmentCreationRequest;
import com.polytech.courses.equipmentservice.constant.EquipmentType;
import com.polytech.courses.equipmentservice.entity.Equipment;
import com.polytech.courses.equipmentservice.repository.EquipmentRepository;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EquipmentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEquipments_should_returnEquipmentList_when_validRequestProvided() throws Exception {
        // GIVEN
        // Inputs
        final EquipmentType inputType = EquipmentType.HAT;

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

        final Equipment ignoredEquipment = Equipment.builder()
            .id(new ObjectId())
            .name("Test #3")
            .picture("Test picture")
            .type(EquipmentType.WEAPON)
            .description("Test description")
            .build();

        equipmentRepository.saveAll(List.of(expectedEquipment1, expectedEquipment2, ignoredEquipment));

        // WHEN
        mockMvc.perform(
                get("/equipments")
                    .param("types", inputType.name())
                    .contentType(MediaType.APPLICATION_JSON)
            )

            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.equipments.[0].name").value(expectedEquipment1.getName()))
            .andExpect(jsonPath("$.equipments.[0].description").value(expectedEquipment1.getDescription()))
            .andExpect(jsonPath("$.equipments.[0].picture").value(expectedEquipment1.getPicture()))
            .andExpect(jsonPath("$.equipments.[0].id").isString())
            .andExpect(jsonPath("$.equipments.size()").value(2));
    }

    @Test
    void createEquipment_should_returnBadRequest_when_requestWithoutNameProvided() throws Exception {
        // GIVEN
        // Inputs
        final EquipmentCreationRequest request = new EquipmentCreationRequest(
            null,
            "Test description",
            EquipmentType.HAT,
            "Test picture"
        );

        // WHEN
        mockMvc.perform(
                post("/equipments")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )

            // THEN
            .andExpect(status().isBadRequest());
    }
}
