package com.felipe.garrido.clientCRUD.repository;

import com.felipe.garrido.clientCRUD.models.Clients;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsRepository extends MongoRepository<Clients, String> {

    Optional<Clients> findByRut(String rut);
    Boolean existsByRut(String rut);

    Boolean existsByEmail(String name);
}
