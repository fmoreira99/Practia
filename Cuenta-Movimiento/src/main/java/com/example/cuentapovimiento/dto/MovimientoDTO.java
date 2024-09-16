package com.example.cuentapovimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
  //  private LocalDateTime fecha;
    private String cuenta;
    private String tipoMovimiento;
    private BigDecimal valor;
    //private BigDecimal saldo;
}
