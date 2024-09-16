package com.banco.clientespersonas.service;

import com.banco.clientespersonas.dto.ClienteDTO;
import com.banco.clientespersonas.entity.Cliente;

import java.util.List;

public interface ClienteService {
    ClienteDTO crearCliente(Cliente cliente);
    ClienteDTO obtenerClientePorId(Long id);
    List<ClienteDTO> obtenerTodosLosClientes();
    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);
    void eliminarCliente(Long id);
}
