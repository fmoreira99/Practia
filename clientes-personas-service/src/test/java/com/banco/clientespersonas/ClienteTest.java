package com.banco.clientespersonas;

import com.banco.clientespersonas.entity.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ClienteTest {
    @Test
    void testCreacionCliente() {
            Cliente cliente = new Cliente();
            cliente.setId(1L);
            cliente.setNombre("Juan Pérez");
            cliente.setGenero("Masculino");
            cliente.setEdad(30);
            cliente.setIdentificacion("1234567890");
            cliente.setDireccion("Calle Principal 123");
            cliente.setTelefono("555-1234");
            cliente.setClienteId("CLI001");
            cliente.setContrasena("password123");
            cliente.setEstado(true);

            assertEquals(1L, cliente.getId());
            assertEquals("Juan Pérez", cliente.getNombre());
            assertEquals("Masculino", cliente.getGenero());
            assertEquals(30, cliente.getEdad());
            assertEquals("1234567890", cliente.getIdentificacion());
            assertEquals("Calle Principal 123", cliente.getDireccion());
            assertEquals("555-1234", cliente.getTelefono());

            assertEquals("CLI001", cliente.getClienteId());
            assertEquals("password123", cliente.getContrasena());
            assertTrue(cliente.isEstado());
    }
}
