package com.felipe.garrido.clientCRUD.repository;

import com.felipe.garrido.clientCRUD.models.Clients;
import com.felipe.garrido.clientCRUD.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {

    Optional<Users> findByRut(String rut);

    Boolean existsByUsername(String username); //AuthController

    Boolean existsByRut(String rut); //AuthController

    Boolean existsByEmail(String email); //AuthController
}
