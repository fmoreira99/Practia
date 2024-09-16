package com.example.cuentapovimiento.service;
import com.example.cuentapovimiento.model.Cuenta;
import com.example.cuentapovimiento.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.util.List;
import java.util.Optional;
@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    public List<Cuenta> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll();
    }

    @Transactional
    public Optional<Cuenta> actualizarCuenta(Long id, Cuenta cuentaActualizada) {
        return cuentaRepository.findById(id)
                .map(cuentaExistente -> {
                    cuentaExistente.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
                    cuentaExistente.setTipoCuenta(cuentaActualizada.getTipoCuenta());
                    cuentaExistente.setSaldoInicial(cuentaActualizada.getSaldoInicial());
                    cuentaExistente.setEstado(cuentaActualizada.getEstado());
                    cuentaExistente.setClienteId(cuentaActualizada.getClienteId());
                    return cuentaRepository.save(cuentaExistente);
                });
    }

    @Transactional
    public boolean eliminarCuenta(Long id) {
        return cuentaRepository.findById(id)
                .map(cuenta -> {
                    cuentaRepository.delete(cuenta);
                    return true;
                })
                .orElse(false);
    }
}