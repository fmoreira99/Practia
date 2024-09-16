package com.example.cuentapovimiento.controller;

import com.example.cuentapovimiento.dto.MovimientoDTO;
import com.example.cuentapovimiento.model.Movimiento;
import com.example.cuentapovimiento.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService movimientoService;

    @Autowired
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping()
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody MovimientoDTO movimiento) {
        Movimiento nuevoMovimiento = movimientoService.registrarMovimiento(movimiento);
        return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimiento(@PathVariable Long id) {
        return movimientoService.obtenerMovimientoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cuenta/{numeroCuenta}")
    public ResponseEntity<List<Movimiento>> obtenerMovimientosPorCuenta(@PathVariable String numeroCuenta) {
        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<Movimiento>> obtenerReporteMovimientos(
            @RequestParam String numeroCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuentaYFecha(numeroCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(movimientos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        Movimiento movimientoActualizado = movimientoService.actualizarMovimiento(id, movimiento);
        return ResponseEntity.ok(movimientoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
