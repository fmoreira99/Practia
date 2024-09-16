package com.example.cuentapovimiento.repository;

import com.example.cuentapovimiento.model.Cuenta;
import com.example.cuentapovimiento.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaAndFechaBetween(Cuenta cuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Movimiento> findByCuentaNumeroCuenta(String numeroCuenta);
    List<Movimiento> findByCuentaClienteId(Long clienteId);
    Movimiento findTopByCuentaOrderByFechaDesc(Cuenta cuenta);
    long countByCuentaAndFechaBetween(Cuenta cuenta, LocalDateTime inicioDelDia, LocalDateTime finDelDia);
}
