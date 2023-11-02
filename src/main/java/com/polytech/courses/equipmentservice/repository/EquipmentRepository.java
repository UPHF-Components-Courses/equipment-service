package com.polytech.courses.equipmentservice.repository;

import com.polytech.courses.equipmentservice.constant.EquipmentType;
import com.polytech.courses.equipmentservice.entity.Equipment;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface EquipmentRepository extends MongoRepository<Equipment, ObjectId> {

    List<Equipment> findAllByTypeIn(Iterable<EquipmentType> types);
}
