package com.banco.clientespersonas;
import com.banco.clientespersonas.dto.ClienteDTO;
import com.banco.clientespersonas.entity.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

        @Autowired
        private DataSource dataSource;

        private JdbcTemplate jdbcTemplate;

        @BeforeEach
        void setUp() {
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute("DELETE FROM cliente");
            jdbcTemplate.execute("DELETE FROM personas");
        }

        @Test
        void testCrearCliente() throws Exception {
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);
            Cliente cliente = new Cliente();
            cliente.setNombre("Ana Lopez");
            cliente.setGenero("Femenino");
            cliente.setEdad(25);
            cliente.setIdentificacion("ID-" + uniqueId);
            cliente.setDireccion("Avenida Central 456");
            cliente.setTelefono("555-5678");
            cliente.setClienteId("CLI-" + uniqueId);
            cliente.setContrasena("securepass");
            cliente.setEstado(true);

            String clienteJson = objectMapper.writeValueAsString(cliente);
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/clientes")
                            .content(clienteJson)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nombre").value("Ana Lopez"))
                    .andExpect(jsonPath("$.identificacion").value(cliente.getIdentificacion()))
                    .andExpect(jsonPath("$.clienteId").value(cliente.getClienteId()))
                    .andReturn();
            if (result.getResponse().getStatus() != 201) {
                System.out.println("Response body: " + result.getResponse().getContentAsString());
                fail("Expected status 201, but got " + result.getResponse().getStatus());
            }
            String responseBody = result.getResponse().getContentAsString();
            ClienteDTO responseDTO = objectMapper.readValue(responseBody, ClienteDTO.class);
            assertNotNull(responseDTO);
            assertEquals("Ana Lopez", responseDTO.getNombre());
            assertEquals(cliente.getIdentificacion(), responseDTO.getIdentificacion());
            assertEquals(cliente.getClienteId(), responseDTO.getClienteId());
        }
    }