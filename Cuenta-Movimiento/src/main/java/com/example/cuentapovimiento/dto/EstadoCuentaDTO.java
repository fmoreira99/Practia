package com.example.cuentapovimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCuentaDTO {
    private Long clienteId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<CuentaDTO> cuentas;
}
