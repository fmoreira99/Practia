package com.example.cuentapovimiento.dto;
import com.example.cuentapovimiento.model.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDTO {
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private List<MovimientoDTO> movimientos;
}
