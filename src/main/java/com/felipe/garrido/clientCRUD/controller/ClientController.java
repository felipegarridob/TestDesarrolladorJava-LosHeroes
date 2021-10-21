package com.felipe.garrido.clientCRUD.controller;

import com.felipe.garrido.clientCRUD.payload.response.MessageResponse;
import com.felipe.garrido.clientCRUD.repository.ClientsRepository;

import com.felipe.garrido.clientCRUD.models.Clients;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/heroes/clients")
public class ClientController {

    @Autowired
    private ClientsRepository clientsRepository;


    @Operation(summary = "Método para agregar un cliente. Acceso solo para usuarios con rol Admin o Moderator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado satisfactoriamente", content = @Content(schema = @Schema(implementation = Clients.class))),
            @ApiResponse(responseCode = "401", description = "Usted no esta autorizado par acceder a este recurso", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addClient(@Valid @RequestBody Clients client) {
        if (clientsRepository.existsByRut(client.getRut())) {
            return ResponseEntity.badRequest().body(new MessageResponse("El rut de cliente ya esta registrado en la base de datos"));
        }
        if (clientsRepository.existsByEmail(client.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("El email de cliente ya esta registrado en la base de datos"));
        }

        Clients newClient = new Clients(
                client.getRut(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress()
        );

        clientsRepository.insert(newClient);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Cliente registrado exitosamente"));
    }


    @Operation(summary = "Método para listar todos los clientes. Acceso para todos los roles de usuarios (User, Moderator, Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios cargados satisfactoriamente", content = @Content(schema = @Schema(implementation = Clients.class))),
            @ApiResponse(responseCode = "401", description = "Usted no esta autorizado para acceder a este recurso", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "403", description = "Está prohibido acceder al recurso al que intentaba acceder", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Clientes no encontrados", content = @Content(schema = @Schema(implementation = Void.class)))
    })

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')  or hasRole('ADMIN') or hasRole('USER') ")
    public List<Clients> getClients() {
        return clientsRepository.findAll();
    }


    @Operation(summary = "Método para actualizar un cliente. Acceso solo para usuarios con rol Admin o Moderator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente actualizado satisfactoriamente", content = @Content(schema = @Schema(implementation = Clients.class))),
            @ApiResponse(responseCode = "401", description = "Usted no esta autorizado para acceder a este recurso", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping("/update/{clientRut}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Clients updateClient(@PathVariable(value = "clientRut") String rutClient,
                                @RequestBody Clients clientDetails) throws ResourceNotFoundException {
        return clientsRepository.findByRut(rutClient).map(client -> {
            client.setFirstName(clientDetails.getFirstName());
            client.setLastName(clientDetails.getLastName());
            client.setAddress(clientDetails.getAddress());
            client.setPhone(clientDetails.getPhone());

            return clientsRepository.save(client);

        }).orElseThrow(() -> new ResourceNotFoundException("Rut:" + rutClient + ", no existe en base de datos"));
    }


    @Operation(summary = "Método para elimiar un cliente. Acceso solo para usuarios con rol Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado satisfactoriamente", content = @Content(schema = @Schema(implementation = Clients.class))),
            @ApiResponse(responseCode = "401", description = "Usted no esta autorizado para acceder a este recurso", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/delete/{clientRut}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteClient(@PathVariable(value = "clientRut") String clientRut) throws ResourceNotFoundException {
        return clientsRepository.findByRut(clientRut).map(client -> {
            clientsRepository.delete(client);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Rut;" + clientRut + " no existe en base de datos"));
    }


    @Operation(summary = "Método para buscar un cliente por rut. Acceso para todos los roles de usuarios (User, Moderator, Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = Clients.class))),
            @ApiResponse(responseCode = "401", description = "Usted no esta autorizado para acceder a este recurso", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "403", description = "Está prohibido acceder al recurso al que intentaba acceder", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content(schema = @Schema(implementation = Void.class)))
    })

    @GetMapping("/getByRut/{clientRut}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Clients> getClientByRut(@Parameter(description = "Rut de cliente para buscar es requerido", required = true)
                                                  @PathVariable(value = "clientRut") String clientRut
    ) throws ResourceNotFoundException {
        Clients client = clientsRepository.findByRut(clientRut)
                .orElseThrow(() -> new ResourceNotFoundException("Rut;" + clientRut + " no existe en base de datos"));
        return ResponseEntity.ok().body(client);
    }

}

