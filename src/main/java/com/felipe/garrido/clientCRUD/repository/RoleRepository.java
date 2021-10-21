package com.felipe.garrido.clientCRUD.repository;

import com.felipe.garrido.clientCRUD.models.ERole;
import com.felipe.garrido.clientCRUD.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
