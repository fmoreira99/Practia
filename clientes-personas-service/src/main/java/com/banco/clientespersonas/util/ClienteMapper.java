package com.banco.clientespersonas.util;

import com.banco.clientespersonas.dto.ClienteDTO;
import com.banco.clientespersonas.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setGenero(cliente.getGenero());
        dto.setEdad(cliente.getEdad());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setClienteId(cliente.getClienteId());
        dto.setEstado(cliente.isEstado());
        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setClienteId(dto.getClienteId());
        cliente.setEstado(dto.isEstado());
        // No mapeamos el id ni la contraseña aquí
        return cliente;
    }

    public void updateEntityFromDTO(ClienteDTO dto, Cliente cliente) {
        if (dto == null || cliente == null) {
            return;
        }

        if (dto.getNombre() != null) {
            cliente.setNombre(dto.getNombre());
        }
        if (dto.getGenero() != null) {
            cliente.setGenero(dto.getGenero());
        }
        cliente.setEdad(dto.getEdad());
        if (dto.getIdentificacion() != null) {
            cliente.setIdentificacion(dto.getIdentificacion());
        }
        if (dto.getDireccion() != null) {
            cliente.setDireccion(dto.getDireccion());
        }
        if (dto.getTelefono() != null) {
            cliente.setTelefono(dto.getTelefono());
        }
        if (dto.getClienteId() != null) {
            cliente.setClienteId(dto.getClienteId());
        }
        cliente.setEstado(dto.isEstado());
        // No actualizamos el id ni la contraseña
    }
}
