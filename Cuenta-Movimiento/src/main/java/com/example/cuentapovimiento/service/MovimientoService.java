package com.example.cuentapovimiento.service;

import com.example.cuentapovimiento.dto.MovimientoDTO;
import com.example.cuentapovimiento.exception.SaldoInsuficienteException;
import com.example.cuentapovimiento.model.Cuenta;
import com.example.cuentapovimiento.model.Movimiento;
import com.example.cuentapovimiento.model.TipoMovimiento;
import com.example.cuentapovimiento.repository.CuentaRepository;
import com.example.cuentapovimiento.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }
    @Transactional
    public Movimiento registrarMovimiento(MovimientoDTO movimiento) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta()).get();
        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal valorMovimiento = movimiento.getValor();
        TipoMovimiento tipo = TipoMovimiento.fromString(movimiento.getTipoMovimiento());
        if (tipo==TipoMovimiento.DEBITO ) {
            if (saldoActual.compareTo(valorMovimiento) < 0) {
                throw new SaldoInsuficienteException("Saldo no disponible");
            }
            saldoActual = saldoActual.subtract(valorMovimiento);
        } else {
            saldoActual = saldoActual.add(valorMovimiento);
        }

        cuenta.setSaldoInicial(saldoActual);
        cuentaRepository.save(cuenta);
        Movimiento Enmovimiento= new Movimiento();

        Enmovimiento.setCuenta(cuenta);
        Enmovimiento.setValor(movimiento.getValor());
        Enmovimiento.setTipoMovimiento(tipo);
        Enmovimiento.setFecha(LocalDateTime.now());
        Enmovimiento.setSaldo(saldoActual);
        return movimientoRepository.save(Enmovimiento);
    }
    public Optional<Movimiento> obtenerMovimientoPorId(Long id) {
        Optional<Movimiento> movi=movimientoRepository.findById(id);
        return movi;
    }
    public List<Movimiento> obtenerMovimientosPorCuenta(String numeroCuenta) {
        return movimientoRepository.findByCuentaNumeroCuenta(numeroCuenta);
    }

    public List<Movimiento> obtenerMovimientosPorCuentaYFecha(String numeroCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return movimientoRepository.findByCuentaAndFechaBetween(cuenta, fechaInicio, fechaFin);
    }

    @Transactional
    public Movimiento actualizarMovimiento(Long id, Movimiento movimientoActualizado) {
        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        Cuenta cuenta = movimientoExistente.getCuenta();
        BigDecimal saldoAnterior = cuenta.getSaldoInicial();
        if (movimientoExistente.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            saldoAnterior = saldoAnterior.add(movimientoExistente.getValor());
        } else {
            saldoAnterior = saldoAnterior.subtract(movimientoExistente.getValor());
        }

        if (movimientoActualizado.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            if (saldoAnterior.compareTo(movimientoActualizado.getValor()) < 0) {
                throw new SaldoInsuficienteException("Saldo no disponible para la actualización");
            }
            saldoAnterior = saldoAnterior.subtract(movimientoActualizado.getValor());
        } else {
            saldoAnterior = saldoAnterior.add(movimientoActualizado.getValor());
        }

        cuenta.setSaldoInicial(saldoAnterior);
        cuentaRepository.save(cuenta);

        movimientoExistente.setTipoMovimiento(movimientoActualizado.getTipoMovimiento());
        movimientoExistente.setValor(movimientoActualizado.getValor());
        movimientoExistente.setSaldo(saldoAnterior);

        return movimientoRepository.save(movimientoExistente);
    }

    @Transactional
    public void eliminarMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        Cuenta cuenta = movimiento.getCuenta();
        BigDecimal saldoActual = cuenta.getSaldoInicial();
        if (movimiento.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            saldoActual = saldoActual.add(movimiento.getValor());
        } else {
            saldoActual = saldoActual.subtract(movimiento.getValor());
        }

        cuenta.setSaldoInicial(saldoActual);
        cuentaRepository.save(cuenta);

        movimientoRepository.deleteById(id);
    }
}
