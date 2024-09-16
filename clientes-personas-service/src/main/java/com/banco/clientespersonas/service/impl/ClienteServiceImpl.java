package com.banco.clientespersonas.service.impl;

import com.banco.clientespersonas.dto.ClienteDTO;
import com.banco.clientespersonas.entity.Cliente;
import com.banco.clientespersonas.exception.ResourceNotFoundException;
import com.banco.clientespersonas.repository.ClienteRepository;
import com.banco.clientespersonas.service.ClienteService;
import com.banco.clientespersonas.util.ClienteMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl  implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Override
    @Transactional
    public ClienteDTO crearCliente(Cliente cliente) {
        cliente = clienteRepository.save(cliente);
        kafkaTemplate.send("cliente-creado-topic", cliente.getId().toString(), cliente.getNombre());
        return clienteMapper.toDTO(cliente);
    }

    @Override
    @Transactional()
    public ClienteDTO obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    @Override
    @Transactional()
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteMapper.updateEntityFromDTO(clienteDTO, cliente);
                    return clienteMapper.toDTO(clienteRepository.save(cliente));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
