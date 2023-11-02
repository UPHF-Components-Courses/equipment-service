package com.polytech.courses.equipmentservice.entity;

import com.polytech.courses.equipmentservice.constant.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "equipments")
public class Equipment {

    @Id
    private ObjectId id;

    private String name;

    private String description;

    private EquipmentType type;

    private String picture;
}
